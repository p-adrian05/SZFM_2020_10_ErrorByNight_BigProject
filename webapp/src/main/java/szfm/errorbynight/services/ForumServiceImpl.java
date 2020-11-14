package szfm.errorbynight.services;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import szfm.errorbynight.model.*;
import szfm.errorbynight.repository.ForumDao;
import szfm.errorbynight.repository.UserDao;
import szfm.errorbynight.util.ThemeStat;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Slf4j
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ForumDao forumDao;

    @Autowired
    private UserDao userDao;


    public boolean addNewTopic(String categoryName, Topic newTopic, User currentUser) {
        Optional<ForumCategory> category = forumDao.getCategoryByName(categoryName);
        if (category.isPresent()) {
            newTopic.setFounderUser(currentUser);
            newTopic.setForumCategory(category.get());
            try {
                forumDao.addTopic(newTopic);
                return true;
            } catch (Exception e) {
                log.info(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public int getTopicsCount(String themeName) {
        return 0;
    }

    @Override
    public boolean addNewPostToTopic(User currentUser, String topicName, String postMessage) {
        return false;
    }

    @Override
    public int getTopicPostsCount(String topicName) {
        return 0;
    }

    @Override
    public List<Post> getPostsByCategoryName(String topicName, int offset, int range) {
        return null;
    }

    @Override
    public boolean savePost(String topicName, Post post, User senderUser) {
        return false;
    }

    @Override
    public List<ThemeStat> getTopicsAndPostsCount(String categoryName, int offset, int range) {
        return null;
    }

    @Override
    public Map<ForumCategory, Integer> getAllForumCategoriesAndTopicsCount() {
        return null;
    }

    @Override
    public List<String> getFavouriteTopicNames(Long userId) {
        return null;
    }

    public boolean addNewFavouriteTopic(String topicName, User currentUser) {
        Optional<Topic> topic = forumDao.getTopicByName(topicName);
        currentUser.setFavTopics(new HashSet<>(forumDao.getFavTopics(currentUser.getId())));
        if(topic.isPresent()){
            currentUser.addFavTopic(topic.get());
            try {
                userDao.save(currentUser);
                return true;
            } catch (Exception e) {
                log.info(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean deleteFavouriteTopic(String topicName, User currentUser) {
        Optional<Topic> topic = forumDao.getTopicByName(topicName);
        currentUser.setFavTopics(new HashSet<>(forumDao.getFavTopics(currentUser.getId())));
        if(topic.isPresent()){
            currentUser.removeFavTopic(topic.get());
            try {
                userDao.save(currentUser);
                return true;
            } catch (Exception e) {
                log.info(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
