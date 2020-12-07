package szfm.errorbynight.util;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import szfm.errorbynight.WebMain;
import szfm.errorbynight.config.TestDataSource;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebMain.class, TestDataSource.class})
@ActiveProfiles("test")
class UtilServiceTest {

    @Test
    void validateUploadedProfileImage() {
        MultipartFile image = new MockMultipartFile("image", "image.png","image", "image".getBytes());
        assertEquals("image.png",image.getOriginalFilename());
        assertDoesNotThrow(()->UtilService.validateUploadedProfileImage(image));
        MultipartFile image2 = new MockMultipartFile("image", "image.fk","image", "image".getBytes());
        assertThrows(FileUploadException.class,()->UtilService.validateUploadedProfileImage(image2));
    }

}
