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

    @Test
    void generateKey() {
        String key = UtilService.generateKey();
        assertEquals(key.length(), 16);
    }

    @Test
    void testGetPageOffsets() {
        int messageCount = 10;
        int range = 3;
       Map<Integer,Integer> offsets = new LinkedHashMap<>();
       offsets.put(1,3);
       offsets.put(4,6);
       offsets.put(7,9);
       offsets.put(10,10);
       assertEquals(offsets.toString(),UtilService.getPageOffsets(messageCount,range).toString());
    }

    @Test
    void testCalculateOffsets() {
        int messageCount = 16;
        int range = 3;
        List<Integer> minOffsets = new LinkedList<>();
        Map<Integer,Integer> pageOffsets = UtilService.getPageOffsets(messageCount,range);
        for(Map.Entry<Integer,Integer> entry: pageOffsets.entrySet()){
            minOffsets.add(entry.getKey());
        }
        assertEquals(1,(UtilService.calculateOffset(minOffsets,1)));
        assertEquals(1,(UtilService.calculateOffset(minOffsets,2)));
        assertEquals(1,(UtilService.calculateOffset(minOffsets,3)));
        assertEquals(4,(UtilService.calculateOffset(minOffsets,4)));
        assertEquals(4,(UtilService.calculateOffset(minOffsets,5)));
        assertEquals(4,(UtilService.calculateOffset(minOffsets,6)));
        assertEquals(7,(UtilService.calculateOffset(minOffsets,7)));
        assertEquals(7,(UtilService.calculateOffset(minOffsets,8)));
        assertEquals(7,(UtilService.calculateOffset(minOffsets,9)));
        assertEquals(10,(UtilService.calculateOffset(minOffsets,10)));
        assertEquals(10,(UtilService.calculateOffset(minOffsets,11)));
        assertEquals(10,(UtilService.calculateOffset(minOffsets,12)));
        assertEquals(13,(UtilService.calculateOffset(minOffsets,13)));
        assertEquals(13,(UtilService.calculateOffset(minOffsets,14)));
        assertEquals(13,(UtilService.calculateOffset(minOffsets,15)));
        assertEquals(16,(UtilService.calculateOffset(minOffsets,16)));
        assertEquals(16,(UtilService.calculateOffset(minOffsets,20)));
    }

}
