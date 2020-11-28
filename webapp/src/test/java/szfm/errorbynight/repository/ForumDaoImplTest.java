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
}