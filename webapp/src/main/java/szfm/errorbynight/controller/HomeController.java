package szfm.errorbynight.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.engine.AttributeNames;
import szfm.errorbynight.model.User;

import javax.servlet.http.HttpSession;

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
}
