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
                .getConversationUsernamesAndNewMessageCount(((User) session.getAttribute("currentUser")), range,offset);
        System.out.println(usernamesAndNewMessage.size());
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

    @GetMapping(Mappings.LIST_MESSAGES + "/{conversationPartnerName}")
    public String listMessages(@PathVariable("conversationPartnerName") String username,
                               @RequestParam(value = "offset", defaultValue = "0") int offset, Model model) {
        Long senderId = userService.getUserIdByName(username);
        User currentUser = ((User) session.getAttribute("currentUser"));
        int range = environment.getProperty("application.message.user.range", Integer.class);
        int allMessagesCount = userService.getMessagesCount(senderId, senderId);

        List<Message> messages = new LinkedList<>();
        Map<Message, Integer> newMessagesNumber;
        List<String> newMessagesTimestamp = new LinkedList<>();
        Map<Integer, Integer> pagesOffsets = UtilService.getPageOffsets(allMessagesCount, range);
        if (offset == 0) {
            List<Integer> minOffsets = new LinkedList<>();
            for (Map.Entry<Integer, Integer> entry : pagesOffsets.entrySet()) {
                minOffsets.add(entry.getKey());
            }
            if (userService.getNewMessages(senderId,currentUser.getId()).size()>0) {
                newMessagesNumber = userService.getNewMessagesAndPlace(senderId, currentUser.getId());
                for (Map.Entry<Message, Integer> entry : newMessagesNumber.entrySet()) {
                    newMessagesTimestamp.add(entry.getKey().getTimestamp());
                    messages.add(entry.getKey());
                }
                List<Integer> newMessagesPlace = new LinkedList<>(newMessagesNumber.values());
                offset = UtilService.calculateOffset(minOffsets, newMessagesPlace.get(newMessagesPlace.size() - 1));
                userService.readMessages(messages);
            } else {
                offset = minOffsets.get(minOffsets.size() - 1);
            }
        }
        messages = userService.getMessages(currentUser.getId(), senderId, offset, range);
        model.addAttribute("messages", messages);
        model.addAttribute("lowerLimitBack", offset - range);
        model.addAttribute("lowerLimitForward", offset + range);
        model.addAttribute("conversationPartnerName", username);
        model.addAttribute("pagesOffsets", pagesOffsets);
        model.addAttribute("range", range);
        model.addAttribute("allMessagesCount", allMessagesCount);
        model.addAttribute("offset", offset);
        model.addAttribute("newMessagesTimestamp", newMessagesTimestamp);
        return "listPrivateMessages";
    }


}
