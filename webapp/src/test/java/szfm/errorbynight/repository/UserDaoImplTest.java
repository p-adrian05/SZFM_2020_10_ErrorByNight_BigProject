package szfm.errorbynight.repository;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import szfm.errorbynight.WebMain;
import szfm.errorbynight.config.TestDataSource;
import szfm.errorbynight.model.Message;
import szfm.errorbynight.model.User;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebMain.class, TestDataSource.class})
@ActiveProfiles("test")
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    private static final String username = "adrian";
    private static final String email = "adrian@gmail.com";
    private static final String activation = "12er";

    @Test
    void findByUsername() {
        Optional<User> found = userDao.findByUsername(username);
        if(found.isPresent()){
            assertEquals(username,found.get().getUsername());
        }else{
            fail("no data");
        }
    }

    @Test
    void getIdByUsername() {
        Optional<User> found = userDao.findByUsername(username);
        Optional<Long> id = userDao.getIdByUsername(username);
        if(found.isPresent() && id.isPresent()){
            assertEquals(id.get(),found.get().getId());
        }else{
            fail("no data");
        }
    }

    @Test
    void getIdByEmail() {
        Optional<User> found = userDao.findByUsername(username);
        Optional<Long> id = userDao.getIdByEmail(email);
        if(found.isPresent() && id.isPresent()){
            assertEquals(id.get(),found.get().getId());
        }else{
            fail("no data");
        }
    }

    @Test
    void findByActivation() {
        Optional<User> found = userDao.findByActivation(activation);
        if(found.isPresent()){
            assertEquals(username, found.get().getUsername());
        }else{
            fail("no data");
        }
    }

    @Test
    void findById() {
        Optional<User> found = userDao.findByUsername(username);
        if(found.isPresent()){
            Optional<User> foundById = userDao.findById(found.get().getId());
            if(foundById.isPresent()){
                assertEquals(found.get(),foundById.get());
            }else{
                fail("no data");
            }
        }else{
            fail("no data");
        }
    }
}
