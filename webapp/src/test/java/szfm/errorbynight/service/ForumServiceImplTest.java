package szfm.errorbynight.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import szfm.errorbynight.WebMain;
import szfm.errorbynight.model.*;
import szfm.errorbynight.repository.ForumDao;
import szfm.errorbynight.repository.UserDao;
import szfm.errorbynight.util.ThemeStat;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebMain.class})
class ForumServiceImplTest {

    @MockBean
    private ForumDao forumDao;

    @MockBean
    private UserDao userDao;

    @Autowired
    private ForumService forumService;

    private User testUser;

    private ForumCategory testCategory;
    private String topicName;

    @BeforeEach
    void setUp(){
        testUser = User.builder()
                .activation("1221")
                .created(ZonedDateTime.now())
                .email("email@email.com")
                .lastLogin(new Date())
                .password("21212121")
                .roles(new HashSet<>())
                .id(1l)
                .username("test").favTopics(new HashSet<>()).enabled(true).build();
        testUser.addRole(new Role("User"));
        testCategory = ForumCategory.builder()
                .description("test forum category")
                .id(1l)
                .title("categoryName")
                .build();
        topicName =  "topicName";
        Map<ForumCategory,Integer> categoriesAndTopicsCountTestData = new LinkedHashMap<>();
        categoriesAndTopicsCountTestData.put(testCategory,4);
        categoriesAndTopicsCountTestData.put(new ForumCategory(),3);

        Mockito.when(forumDao.getCategoryByName(testCategory.getTitle())).thenReturn(Optional.of(new ForumCategory()));
        Mockito.when(forumDao.getTopicByName(topicName)).thenReturn(Optional.of(new Topic("title")));
        Mockito.when(forumDao.getTopicIdByName(topicName)).thenReturn(Optional.of(1l));
        Mockito.when(forumDao.getFavTopics(testUser.getId())).thenReturn(List.of(new Topic("title1"),new Topic("title2")));
        Mockito.when(forumDao.getCategoryIdByName(testCategory.getTitle())).thenReturn(Optional.of(1l));
        Mockito.when(forumDao.countTopics(testCategory.getId())).thenReturn(1l);
        Mockito.when(forumDao.countTopicPosts(testCategory.getId())).thenReturn(5l);
        Mockito.when(forumDao.getPostsByTopicName(topicName,0,3)).thenReturn(List.of(new Post("content")));
        Mockito.when(forumDao.getPostsByTopicName(topicName,-1,3)).thenReturn(new LinkedList<>());
        Mockito.when(forumDao.getCategoriesAndTopicsCount()).thenReturn(categoriesAndTopicsCountTestData);
        Mockito.when(forumDao.getThemeStat(testCategory.getTitle(),0,3)).thenReturn(List.of(new ThemeStat(),new ThemeStat()));
        Mockito.when(forumDao.getFavouriteTopicNames(testUser.getId())).thenReturn(List.of("topic1","topic2"));
    }

    @Test
    void addNewTopic() {
        assertTrue(forumService.addNewTopic(testCategory.getTitle(),new Topic("title"),testUser));
        assertFalse(forumService.addNewTopic("cat",new Topic("title"),testUser));
    }

    @Test
    void addNewFavouriteTopic() {
        assertTrue(forumService.addNewFavouriteTopic(topicName,testUser));
        assertFalse(forumService.addNewFavouriteTopic("topicNotFound",testUser));
    }

    @Test
    void deleteFavouriteTopic() {
        assertTrue(forumService.deleteFavouriteTopic(topicName,testUser));
        assertFalse(forumService.deleteFavouriteTopic("topicNotFound",testUser));
    }

    @Test
    void getTopicsCount() {
        assertEquals(1,forumService.getTopicsCount(testCategory.getTitle()));
        assertEquals(0,forumService.getTopicsCount("no cat"));
    }

    @Test
    void addNewPostToTopic() {
        assertTrue(forumService.addNewPostToTopic(testUser,topicName,"Postmessage"));
        assertFalse(forumService.addNewPostToTopic(testUser,"no topic","Postmessage"));
    }

    @Test
    void getTopicPostsCount() {
        assertEquals(5,forumService.getTopicPostsCount(topicName));
        assertEquals(0,forumService.getTopicPostsCount("not topic"));
    }

    @Test
    void getPostsByTopicName() {
        assertEquals(1,forumService.getPostsByTopicName(topicName,1,3).size());
        assertNotEquals(1,forumService.getPostsByTopicName(topicName,0,3).size());
    }

    @Test
    void savePost() {
        assertTrue(forumService.savePost(topicName,new Post("content"),testUser));
        assertFalse(forumService.savePost("no topic",new Post("content"),testUser));
    }

    @Test
    void getAllForumCategoriesAndTopicsCount() {
        assertEquals(2,forumService.getAllForumCategoriesAndTopicsCount().size());
    }

    @Test
    void getTopicsAndPostsCount() {
        assertEquals(2,forumService.getTopicsAndPostsCount(testCategory.getTitle(),1,3).size());
        assertEquals(0,forumService.getTopicsAndPostsCount("no category",1,3).size());
    }

    @Test
    void getFavouriteTopicNames() {
        assertEquals(2,forumService.getFavouriteTopicNames(testUser.getId()).size());
        assertEquals(0,forumService.getFavouriteTopicNames(3l).size());
    }
}
