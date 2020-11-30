package szfm.errorbynight.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;
import szfm.errorbynight.model.User;
import szfm.errorbynight.services.ForumService;
import szfm.errorbynight.services.StorageService;
import szfm.errorbynight.util.AttributeNames;
import szfm.errorbynight.util.Mappings;
import szfm.errorbynight.util.ViewNames;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private HttpSession session;

    @Autowired
    private StorageService storageService;

    @GetMapping(Mappings.LOGIN)
    public String login(@ModelAttribute(AttributeNames.ACTIVATION_RESULT) String activationResult) {
        return ViewNames.LOGIN;
    }

    @GetMapping(value = {"/",ViewNames.HOME})
    public String home(Model model, Authentication authentication) {
        model.addAttribute("categoriesAndTopicsCount", forumService.getAllForumCategoriesAndTopicsCount());
        if (authentication != null) {
            model.addAttribute("favTopics", forumService.getFavouriteTopicNames(((User) session.getAttribute("currentUser")).getId()));
        }
        return ViewNames.HOME;
    }

    @GetMapping(value = Mappings.IMAGES+"/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getProfileImage(@PathVariable String filename, HttpServletResponse response) throws IOException {
        try{
            InputStream in = storageService.loadAsResource(filename).getInputStream();
            byte[] data = in.readAllBytes();
            in.close();
            return data;
        }catch (ResourceAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Image not found",e);
        }
    }
}
