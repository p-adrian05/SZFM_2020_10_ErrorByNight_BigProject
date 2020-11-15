package szfm.errorbynight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import szfm.errorbynight.model.Post;
import szfm.errorbynight.model.User;
import szfm.errorbynight.services.ForumService;
import szfm.errorbynight.util.UtilService;
import szfm.errorbynight.util.Mappings;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class PostController {

    @Autowired
    private ForumService forumService;
    @Autowired
    private Environment environment;
    @Autowired
    private HttpSession session;

    @GetMapping(Mappings.THEME + "/{topicName}")
    public String listTopicPosts(@PathVariable("topicName") String topicName,
                                 Model model,
                                 @RequestParam(value = "offset", defaultValue = "1") int offset,
                                 @RequestParam(value = "range", defaultValue = "0") int range,
                                 Authentication authentication) {
        int tempRange = environment.getProperty("application.posts.range", Integer.class);
        Map<Integer, Integer> pagesOffsets;
        if (range == 0 || range > tempRange) {
            range = tempRange;
        }
        if (range == 1) {
            pagesOffsets = UtilService.getPageOffsets(1, range);
        } else {
            int postsAmount = forumService.getTopicPostsCount(topicName);
            pagesOffsets = UtilService.getPageOffsets(postsAmount, range);
        }
        model.addAttribute("posts", forumService.getPostsByCategoryName(topicName, offset, range));
        model.addAttribute("pagesOffsets", pagesOffsets);
        model.addAttribute("offset", offset);
        model.addAttribute("range", range);
        model.addAttribute("topicName", topicName);
        model.addAttribute("lowerLimitBack", offset - range);
        model.addAttribute("lowerLimitForward", offset + range);
        if (authentication != null) {
            model.addAttribute("authenticatedUsername", authentication.getName());
            model.addAttribute("favTopics", forumService.getFavouriteTopicNames(((User) session.getAttribute("currentUser")).getId()));
        }
        return "listPosts";
    }

    @GetMapping(Mappings.NEW_POST_THEME + "/{topicName}")
    public String newPost(@PathVariable String topicName,
                          @RequestParam(value = "userToSend", defaultValue = "") String userNameToSend,
                          @RequestParam(value = "offset", defaultValue = "0") int offset, Model model) {
        Post answerToPost = null;
        if (offset != 0) {
            answerToPost = forumService.getPostsByCategoryName(topicName, offset, 1).get(0);
            model.addAttribute("userNameToSend", userNameToSend);
        }

        model.addAttribute("topicName", topicName);
        model.addAttribute("post", answerToPost);
        model.addAttribute("offset", offset);
        return "postAnswerForm";
    }

    @PostMapping(Mappings.NEW_POST_THEME + "/{topicName}")
    public String processNewPost(@PathVariable String topicName,
                                 @RequestParam(value = "userToSend", defaultValue = "") String userNameToSend,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam("message") String message,
                                 Model model) {
        Post post = new Post(message);
        Post parentPost = null;
        if (message == null || message.equals("") || message.contains(" ")) {
            model.addAttribute("topicName", topicName);
            model.addAttribute("result", false);
            return "postAnswerForm";
        }
        if (offset != 0) {
            parentPost = forumService.getPostsByCategoryName(topicName, offset, 1).get(0);
            model.addAttribute("post", parentPost);
            model.addAttribute("userNameToSend", userNameToSend);
        }
        post.setParentPostOffset(offset);
        post.setParentPost(parentPost);

        boolean result = forumService.savePost(topicName, post, ((User) session.getAttribute("currentUser")));
        model.addAttribute("topicName", topicName);
        model.addAttribute("result", result);
        return "postAnswerForm";
    }
}
