package szfm.errorbynight.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import szfm.errorbynight.WebMain;
import szfm.errorbynight.config.TestDataSource;
import szfm.errorbynight.model.ForumCategory;
import szfm.errorbynight.model.Post;
import szfm.errorbynight.model.Topic;
import szfm.errorbynight.util.ThemeStat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebMain.class, TestDataSource.class})
@ActiveProfiles("test")
class ForumDaoImplTest {

    @Autowired
    private ForumDao forumDao;

    @Test
    void getCategoryByName() {
         Optional<ForumCategory> forumCategory = forumDao.getCategoryByName("testCategory");
         if(forumCategory.isPresent()){
             assertEquals("testCategory",forumCategory.get().getTitle());
             assertNotEquals(forumCategory.get().getTitle(),"testCategory2");
         }else{
             fail("no data");
         }
    }

    @Test
    void getCategoriesAndTopicsCount() {
        Map<ForumCategory,Integer> data = forumDao.getCategoriesAndTopicsCount();
        data.forEach((key,value)-> {
            if(key.getTitle().equals("testCategory")){
                assertEquals(2,value.intValue());
            }
        });
    }

     @Test
    void getThemeStat() {
        List<ThemeStat> themeStats = forumDao.getThemeStat("testCategory",0,3);
        assertEquals(2,themeStats.get(0).getTopicCount());
        assertEquals(1,themeStats.get(1).getTopicCount());
        assertEquals(2, themeStats.size());
        themeStats = forumDao.getThemeStat("testCategory",1,3);
        assertEquals(1, themeStats.size());
        themeStats = forumDao.getThemeStat("testCategory",-1,3);
        assertEquals(0, themeStats.size());
        themeStats = forumDao.getThemeStat("testCategory",2,3);
        assertEquals(0, themeStats.size());
    }

        @Test
    void getTopicByName() {
        Optional<Topic> topic = forumDao.getTopicByName("testTopic");
        if(topic.isPresent()){
            assertEquals("testTopic",topic.get().getTitle());
        }else {
            fail("no data");
        }
    }

      @Test
    void getCategoryIdByName() {
      Optional<Long> categoryId =  forumDao.getCategoryIdByName("testCategory");
      if(categoryId.isPresent()){
          assertEquals(1,categoryId.get().intValue());
      }else{
          fail("no data");
      }
    }

    @Test
    void countTopics() {
        Optional<Long> categoryId =  forumDao.getCategoryIdByName("testCategory");
        if(categoryId.isPresent()){
            assertEquals(1,categoryId.get().intValue());
            assertEquals(2,forumDao.countTopics(categoryId.get()).intValue());
        }else{
            fail("no data");
        }
    }

     @Test
    void countTopicPosts() {
        Optional<Topic> topic = forumDao.getTopicByName("testTopic2");
        if(topic.isPresent()){
            assertEquals(2,forumDao.countTopicPosts(topic.get().getId()).intValue());
        }else {
            fail("no data");
        }
    }

    @Test
    void getTopicIdByName() {
        Optional<Long> topicId =  forumDao.getTopicIdByName("testTopic2");
        if(topicId.isPresent()){
            assertEquals(2,topicId.get().intValue());
        }else{
            fail("no data");
        }
    }


     @Test
    void getPostsByTopicName() {
        List<Post> posts = forumDao.getPostsByTopicName("testTopic2",0,3);
        assertEquals(2, posts.size());
        posts = forumDao.getPostsByTopicName("testTopic2",1,3);
        assertEquals(1, posts.size());
        posts = forumDao.getPostsByTopicName("testTopic2",-1,3);
        assertEquals(0, posts.size());
        posts = forumDao.getPostsByTopicName("testTopic2",2,3);
        assertEquals(0, posts.size());
    }

      @Test
    void getFavouriteTopicNames() {
        List<String> topicNames = forumDao.getFavouriteTopicNames(22l);
        if(topicNames.size() == 0 ){
            fail("no data");
        }else{
            assertEquals(2,topicNames.size());
            assertEquals("testTopic",topicNames.get(0));
            assertEquals("testTopic2",topicNames.get(1));
        }
    }

    @Test
    void getFavTopics() {
        List<Topic> topics = forumDao.getFavTopics(22l);
        if(topics.size() == 0 ){
            fail("no data");
        }else{
            assertEquals(2,topics.size());
            assertEquals("testTopic",topics.get(0).getTitle());
            assertEquals("testTopic2",topics.get(1).getTitle());
        }
    }


}