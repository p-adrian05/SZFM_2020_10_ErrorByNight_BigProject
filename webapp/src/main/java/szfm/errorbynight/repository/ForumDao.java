package szfm.errorbynight.repository;



import szfm.errorbynight.model.ForumCategory;
import szfm.errorbynight.model.Post;
import szfm.errorbynight.util.ThemeStat;
import szfm.errorbynight.model.Topic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ForumDao {
    Optional<Long> getCategoryIdByName(String categoryName);

    Optional<ForumCategory> getCategoryByName(String categoryName);

    void addTopic(Topic topic);

    Optional<Topic> getTopicByName(String topicName);

    Long countTopics(Long categoryId);

    Map<ForumCategory, Integer> getCategoriesAndTopicsCount();

    void addPost(Post post);

    List<ThemeStat> getThemeStat(String themeName, int lowerLimit, int range);

    Long countTopicPosts(Long topicId);

    Optional<Long> getTopicIdByName(String topicName);

    List<Post> getPostsByTopicName(String topicName, int lowerLimit, int range);

    List<String> getFavouriteTopicNames(Long userId);

    void updateTopic(Topic topic);
    List<Topic> getFavTopics(Long userid);
}
