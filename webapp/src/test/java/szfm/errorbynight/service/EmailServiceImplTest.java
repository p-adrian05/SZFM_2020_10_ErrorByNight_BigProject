package szfm.errorbynight.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import szfm.errorbynight.services.EmailService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class EmailServiceImplTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmailTest(){
        assertTrue(emailService.sendMessage("adrian16616@gmail.com","userName","23423asd"));
        assertFalse(emailService.sendMessage("sdfgdgf","userName","23423asd"));
    }


}