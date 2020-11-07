package szfm.errorbynight.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;
import szfm.errorbynight.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@SessionAttributes(names = {"currentUser"})
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpSession session;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Optional<User> user = userDao.findByUsername(authentication.getName());
        if(user.isPresent()){
            user.get().setLastLogin(new Date());
            userDao.save(user.get());
            session.setAttribute("currentUser", user.get());
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        response.sendRedirect("home");
    }
}
