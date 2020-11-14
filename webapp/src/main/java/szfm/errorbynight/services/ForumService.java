package szfm.errorbynight.services;


import szfm.errorbynight.model.*;
import szfm.errorbynight.util.ThemeStat;

import java.util.List;
import java.util.Map;

public interface ForumService {

    boolean addNewTopic(String categoryName, Topic newTopic, User currentUser);

    int getTopicsCount(String themeName);
    boolean addNewPostToTopic(User currentUser, String topicName, String postMessage);

    int getTopicPostsCount(String topicName);

    List<Post> getPostsByTopicName(String topicName, int offset, int range);

    boolean savePost(String topicName, Post post, User senderUser);

    List<ThemeStat> getTopicsAndPostsCount(String categoryName, int offset, int range);

    Map<ForumCategory, Integer> getAllForumCategoriesAndTopicsCount();

    List<String> getFavouriteTopicNames(Long userId);

    boolean addNewFavouriteTopic(String topicName, User currentUser);

    boolean deleteFavouriteTopic(String topicName, User currentUser);
}
