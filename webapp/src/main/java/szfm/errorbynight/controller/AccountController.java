package szfm.errorbynight.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import szfm.errorbynight.model.Message;
import szfm.errorbynight.model.User;
import szfm.errorbynight.model.UserData;
import szfm.errorbynight.services.UserService;
import szfm.errorbynight.util.Mappings;
import szfm.errorbynight.util.UtilService;
import szfm.errorbynight.util.ViewNames;

import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping(Mappings.ACCOUNT)
@Controller
@Slf4j
@PropertySource("classpath:/application.properties")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @Autowired
    private HttpSession session;

    @GetMapping(Mappings.SETTINGS)
    public String settings(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/" + Mappings.ACCOUNT + "/" + Mappings.SETTINGS_USERDATA;
        }
        return "";
    }

}
