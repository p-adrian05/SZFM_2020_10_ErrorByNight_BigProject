package szfm.errorbynight.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
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

    @GetMapping(Mappings.PROFILE + "/{user}")
    public String profile(@PathVariable("user") String username, Model model) {
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("currentUser"));
        if (user.isPresent() && username.equals(user.get().getUsername())) {
            model.addAttribute("user", user.get());
            return ViewNames.PROFILE;
        } else {
            user = userService.findByUsername(username);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return ViewNames.PROFILE;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
            }
        }
    }

    @GetMapping(Mappings.SETTINGS_USERDATA)
    public String userdata(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("userdata", ((User) session.getAttribute("currentUser")).getUserData());
        }
        return ViewNames.USERDATA;
    }
    @PostMapping(Mappings.SETTINGS_USERDATA)
    public String processUserdata(@ModelAttribute("userdata") UserData userData, Model model,
                                  @RequestParam("profileImgFile") MultipartFile profileImgFile) {
        if (!Objects.equals(profileImgFile.getOriginalFilename(), "")) {
            String extension = environment.getProperty("user.profile.img.extension");
//            try {
//                String imageName = userData.getUserId() + extension;
//                storageService.store(profileImgFile,imageName);
//                userData.setProfileImg(imageName);
//            } catch (FileUploadException e) {
//                model.addAttribute("imgError", e.getMessage());
//                return ViewNames.USERDATA;
//            }
        }
        log.info(userData.getProfileImg());
        ((User) session.getAttribute("currentUser")).setUserData(userData);
        boolean result = userService.saveUserData(userData);
        model.addAttribute("saveResult", result);
        return ViewNames.USERDATA;
    }

    @GetMapping(Mappings.PRIVATE_MESSAGE + "/{usernameTo}")
    public String sendPrivateMessage(@PathVariable("usernameTo") String usernameTo, Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("userToSend", usernameTo);
        }
        return "privateMessageForm";
    }

    @PostMapping(Mappings.PRIVATE_MESSAGE + "/{usernameTo}")
    public String processPrivateMessage(@PathVariable("usernameTo") String usernameTo,
                                        @RequestParam("message") String message,
                                        Model model) {
        boolean result = userService.sendMessage(usernameTo, (User) session.getAttribute("currentUser"), message);
        log.info(message);
        model.addAttribute("result", result);
        model.addAttribute("userToSend", usernameTo);
        return "privateMessageForm";
    }
    @GetMapping(value = Mappings.MESSAGES)
    public String messagesMainPage(@RequestParam(value = "offset", defaultValue = "1") int offset,
                                   Model model) {

        int range = environment.getProperty("application.message.range", Integer.class);
        Map<String, Integer> usernamesAndNewMessage = userService
                .getConversationUsernamesAndNewMessageCount(((User) session.getAttribute("currentUser")), offset, range);
        model.addAttribute("usernamesAndNewMessage", usernamesAndNewMessage);
        model.addAttribute("lowerLimitBack", offset - range);
        model.addAttribute("lowerLimitForward", offset + range);
        model.addAttribute("pagesOffsets",
                UtilService.getPageOffsets(userService.getConversationUsernames(((User) session.getAttribute("currentUser")),
                        1, Integer.MAX_VALUE).size(),range));
        model.addAttribute("range", range);
        model.addAttribute("offset", offset);
        return "privateMessages";
    }


}
