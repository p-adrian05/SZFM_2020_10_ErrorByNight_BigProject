package szfm.errorbynight.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szfm.errorbynight.model.Role;
import szfm.errorbynight.model.User;
import szfm.errorbynight.model.UserData;
import szfm.errorbynight.repository.RoleDao;
import szfm.errorbynight.repository.UserDao;
import szfm.errorbynight.util.UtilService;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService{

    private UserDao userDao;
    private RoleDao roleDao;
    private EmailService emailService;
    public static final String USER_ROLE = "USER";
    private PasswordEncoder passwordEncoder;
    private HttpSession session;


    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, EmailService emailService) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.emailService = emailService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        if (user.isPresent()) {
            session.setAttribute("currentUser",user.get());
            return new UserDetailsImpl(user.get());
        }
        throw new UsernameNotFoundException(username);
    }

    @Override
    public void registerUser(User userToRegister) throws SQLException {
        Optional<Long> userIdByUsername = userDao.getIdByUsername(userToRegister.getUsername());
        Optional<Long> userIdByEmail = userDao.getIdByEmail(userToRegister.getEmail());
        if (userIdByUsername.isPresent()) {
            throw new SQLException("Username is taken.");
        }
        if (userIdByEmail.isPresent()) {
            throw new SQLException("Email is taken.");
        }
        Optional<Role> userRole = roleDao.findByRole(USER_ROLE);
        if (userRole.isPresent()) {
            userToRegister.addRole(userRole.get());
        } else {
            userToRegister.addRole(new Role(USER_ROLE));
        }
        String key = UtilService.generateKey();
        UserData userData = new UserData();
        userData.setUser(userToRegister);
        userToRegister.setUserData(userData);
        userToRegister.setEnabled(false);
        userToRegister.setActivation(key);
        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        userDao.add(userToRegister);
        emailService.sendMessage(userToRegister.getEmail(), userToRegister.getUsername(), key);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean activateUser(String code) {
        Optional<User> user = userDao.findByActivation(code);
        if (user.isPresent()) {
            user.get().setEnabled(true);
            user.get().setActivation("");
            userDao.save(user.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean sendMessage(String usernameTo, User userFrom, String message) {
        return false;
    }

    @Override
    public Map<String, Integer> getConversationUsernamesAndNewMessageCount(User user, int range, int lowerLimit) {
        return null;
    }

    @Override
    public List<String> getConversationUsernames(User user, int lowerLimit, int range) {
        return null;
    }

    @Override
    public List<Message> getMessages(Long userId1, Long userId2, int lowerLimit, int range) {
      return userDao.getMessagesByLimit(userId1, userId2, lowerLimit - 1, range);
    }

    @Override
    public Integer getMessagesCount(Long senderId, Long receiverId) {
        return null;
    }

    @Override
    public Map<Message, Integer> getNewMessagesAndPlace(Long senderId, Long receiverId) {
        return null;
    }

    @Override
    public List<Message> getNewMessages(Long senderId, Long receiverId) {
        return null;
    }

    @Override
    public boolean readMessages(List<Message> messages) {
        return false;
    }
}
