package szfm.errorbynight.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import szfm.errorbynight.WebMain;
import szfm.errorbynight.config.TestDataSource;
import szfm.errorbynight.model.Message;
import szfm.errorbynight.model.Role;
import szfm.errorbynight.model.User;
import szfm.errorbynight.repository.RoleDao;
import szfm.errorbynight.repository.UserDao;
import szfm.errorbynight.services.EmailService;
import szfm.errorbynight.services.UserService;
import szfm.errorbynight.util.InvalidUserToRegisterException;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebMain.class})
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @MockBean
    private EmailService emailService;
    @MockBean
    private UserDao userDao;
    @MockBean
    private RoleDao roleDao;

    private User testUser;
    private User testUser2;

    @BeforeEach
    void setUp() {
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
        testUser2 = User.builder()
                .activation("1221")
                .created(ZonedDateTime.now())
                .email("email2@email.com")
                .lastLogin(new Date())
                .password("21212121")
                .roles(new HashSet<>())
                .id(1l)
                .username("test2").favTopics(new HashSet<>()).enabled(true).build();
        testUser.addRole(new Role("User"));
        Mockito.when(userDao.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Mockito.when(userDao.getIdByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser.getId()));
        Mockito.when(userDao.getIdByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser.getId()));
        Mockito.when(userDao.findByActivation(testUser.getActivation())).thenReturn(Optional.of(testUser));
        Mockito.when(userDao.countNewMessagesForUser(testUser.getUsername(),testUser2.getUsername())).thenReturn(0);
        Mockito.when(userDao.countNewMessagesForUser(testUser.getUsername(),testUser.getUsername())).thenReturn(0);
        Mockito.when(roleDao.findByRole("USER")).thenReturn(Optional.of(new Role("USER")));
        Mockito.when(userDao.getConversationUsernames(testUser,1,3))
                .thenReturn(List.of(testUser.getUsername(),testUser2.getUsername()));

        Mockito.when(userDao.getMessagesByLimit(testUser.getId(),testUser2.getId(),0,3)).thenReturn(List.of(new Message("message")));
        Mockito.when(userDao.getNewMessages(testUser.getId(),testUser2.getId())).thenReturn(List.of(new Message("message")));
        Mockito.when(userDao.getMessagesCount(testUser.getId(),testUser2.getId())).thenReturn(3);

    }

    @Test
    void registerUser() {
        assertDoesNotThrow(()->userService.registerUser(testUser2));
        assertThrows(InvalidUserToRegisterException.class,()->userService.registerUser(testUser));
    }


    @Test
    void findByUsername() {
        assertEquals("test",userService.findByUsername("test").get().getUsername());
    }

    @Test
    void getConversationUsernames() {
        assertEquals(List.of(testUser.getUsername(),testUser2.getUsername())
                .toString(),userService.getConversationUsernames(testUser,1,3).toString());
    }

    @Test
    void userActivation() {
        assertTrue(userService.activateUser(testUser.getActivation()));
        assertFalse(userService.activateUser("dfgdf"));
    }

    @Test
    void sendMessage() {
        assertFalse(userService.sendMessage(testUser2.getUsername(),testUser2,"message"));
        assertFalse(userService.sendMessage(testUser2.getUsername(),testUser,""));
        assertFalse(userService.sendMessage(testUser2.getUsername(),testUser,null));
        assertTrue(userService.sendMessage(testUser2.getUsername(),testUser,"message"));
    }


    @Test
    void getMessages() {
        assertEquals(1,userService.getMessages(testUser.getId(), testUser2.getId(), 1, 3).size());
    }

    @Test
    void getMessagesCount() {
        assertEquals(3,userService.getMessagesCount(testUser.getId(), testUser2.getId()));
    }
    @Test
    void getNewMessages() {
        assertEquals(1,userService.getNewMessages(testUser.getId(), testUser2.getId()).size());
    }

    @Test
    void readMessages() {
        Message message = new Message();
        message.setStatus(false);
        List<Message> messages = List.of(message);
        userService.readMessages(messages);
        assertTrue(message.isStatus());
    }

    @Test
    void getUserIdByName() {
        assertEquals(1l,userService.getUserIdByName("test"));
    }
}