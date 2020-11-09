package szfm.errorbynight.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import szfm.errorbynight.model.User;
import szfm.errorbynight.model.UserData;
import szfm.errorbynight.repository.RoleDao;
import szfm.errorbynight.repository.UserDao;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
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
    public void registerUser(User user) throws SQLException {

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean userActivation(String code) {
        Optional<User> user = userDao.findByActivation(code);
        if (user.isPresent()) {
            user.get().setEnabled(true);
            user.get().setActivation("");
            UserData userData = new UserData();
            userData.setUser(user.get());
            user.get().setUserData(userData);
            userDao.save(user.get());
            return true;
        }
        return false;
    }
}
