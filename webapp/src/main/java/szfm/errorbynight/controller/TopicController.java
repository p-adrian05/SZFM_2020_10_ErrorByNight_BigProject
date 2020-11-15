package szfm.errorbynight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import szfm.errorbynight.model.Topic;
import szfm.errorbynight.model.User;
import szfm.errorbynight.services.ForumService;
import szfm.errorbynight.util.UtilService;
import szfm.errorbynight.util.Mappings;
import szfm.errorbynight.util.ViewNames;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class TopicController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private Environment environment;
    @Autowired
    private HttpSession session;

    @GetMapping(Mappings.THEME_NEW + "/{categoryName}")
    public String newTopic(@PathVariable("categoryName") String categoryName, Model model) {
        model.addAttribute("categoryName", categoryName);
        return "newTopic";
    }

    @PostMapping(Mappings.THEME_NEW + "/{categoryName}")
    public String processNewTopic(@PathVariable("categoryName") String categoryName,
                                  @RequestParam("themeName") String themeName,
                                  @RequestParam("starter-comment") String starterComment,
                                  Authentication authentication, RedirectAttributes redirectAttributes) {
        boolean result = false;
        if (authentication != null && !themeName.equals("") && !starterComment.equals("")) {
            Topic newTopic = new Topic(themeName);
            User currentUser = ((User) session.getAttribute("currentUser"));
            if (forumService.addNewTopic(categoryName, newTopic, currentUser)) {
                result = forumService.addNewPostToTopic(currentUser, themeName, starterComment);
            }
            redirectAttributes.addFlashAttribute("result", result);
            if (result) {
                return Mappings.REDIRECT_THEMES + "/" + categoryName;
            }
        }
        redirectAttributes.addFlashAttribute("result", result);
        return Mappings.REDIRECT_THEMES_NEW + "/" + categoryName;
    }

    @GetMapping(Mappings.THEMES + "/{themeName}")
    public String listTopics(@PathVariable("themeName") String themeName,
                             @RequestParam(value = "offset", defaultValue = "1") int offset,
                             Model model, @ModelAttribute("result") String result,
                             Authentication authentication) {
        int range = environment.getProperty("application.topics.range", Integer.class);

        int topicsAmount = forumService.getTopicsCount(themeName);
        Map<Integer, Integer> pagesOffsets = UtilService.getPageOffsets(topicsAmount, range);
        model.addAttribute("topicsAndPostsCount", forumService.getTopicsAndPostsCount(themeName, offset, range));
        model.addAttribute("pagesOffsets", pagesOffsets);
        model.addAttribute("offset", offset);
        model.addAttribute("themeName", themeName);
        model.addAttribute("lowerLimitBack", offset - range);
        model.addAttribute("lowerLimitForward", offset + range);
        model.addAttribute("result", result);
        if (authentication != null) {
            model.addAttribute("favTopics", forumService.getFavouriteTopicNames(((User) session.getAttribute("currentUser")).getId()));
        }
        return "listTopics";
    }


    @GetMapping(Mappings.THEME_ADD_FAV + "/{topicName}")
    public String addFavTopicToUser(@PathVariable String topicName,RedirectAttributes redirectAttributes){
        boolean result = forumService.addNewFavouriteTopic(topicName, ((User) session.getAttribute("currentUser")));
        redirectAttributes.addFlashAttribute("addFavTopicResult", result);
        return "redirect:/"+ ViewNames.HOME;
    }
    @GetMapping(Mappings.THEME_DELETE_FAV + "/{topicName}")
    public String deleteFavTopicToUser(@PathVariable String topicName){
        forumService.deleteFavouriteTopic(topicName, ((User) session.getAttribute("currentUser")));
        return "redirect:/"+ViewNames.HOME;
    }
}
