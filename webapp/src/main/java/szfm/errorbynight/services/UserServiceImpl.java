package szfm.errorbynight.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import szfm.errorbynight.model.User;
import szfm.errorbynight.repository.RoleDao;
import szfm.errorbynight.repository.UserDao;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService{

    private final UserDao userDao;
    private final RoleDao roleDao;


    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
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
        return Optional.empty();
    }

    @Override
    public boolean userActivation(String code) {
        return false;
    }
}
