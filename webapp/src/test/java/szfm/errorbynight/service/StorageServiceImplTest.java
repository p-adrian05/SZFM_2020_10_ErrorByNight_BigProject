package szfm.errorbynight.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class StorageServiceImplTest {

	@Autowired
    private StorageService storageService;

    @Test
    void loadAsResource() {
        assertThrows(ResourceAccessException.class,()->storageService.loadAsResource("default_img.png"));
        assertThrows(ResourceAccessException.class,()->storageService.loadAsResource("default_profile_img"));
        assertDoesNotThrow(()->storageService.loadAsResource("default_profile_img.png"));
    }

}