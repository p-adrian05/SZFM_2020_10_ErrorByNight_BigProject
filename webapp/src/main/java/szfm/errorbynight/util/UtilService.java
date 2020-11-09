package szfm.errorbynight.util;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UtilService {

    public static Map<Integer, Integer> getPageOffsets(int messageCount, int range) {
        Map<Integer, Integer> pagesOffsets = new LinkedHashMap<>();
        int max = 0;
        int min;
        do {
            min = max + 1;
            max += range;
            if (messageCount == 0) {
                break;
            }
            if (messageCount < range) {
                pagesOffsets.put(min, min + messageCount - 1);
                break;
            }
            pagesOffsets.put(min, max);
            messageCount -= range;

        } while (true);
        return pagesOffsets;
    }

    public static int calculateOffset(List<Integer> offsets, Integer value) {
        int offset = 1;
        for (int i = 0; i < offsets.size() - 1; i++) {
            if (value >= offsets.get(i) && value < offsets.get(i + 1)) {
                offset = offsets.get(i);
                break;
            } else {
                offset = offsets.get(offsets.size() - 1);
            }
        }
        return offset;
    }

    public static void validateUploadedProfileImage(MultipartFile image) throws FileUploadException {
        if (image.getSize() > 500000) {
            throw new FileUploadException("Wrong size, image must be max 0.5MB");
        }
        String[] fileExtensions = {"png", "jpg", "jpeg", "gif"};
        int count = 0;
        for (String fileExtension : fileExtensions) {
            if (image.getOriginalFilename().endsWith(fileExtension)) {
                count++;
            }
        }
        if (count == 0) {
            throw new FileUploadException("Wrong file extension,must be jpeg,png,jpg,gif");
        }
    }

    public static String generateKey() {
        Random random = new Random();
        char[] key = new char[16];
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) ('a' + random.nextInt(26));
        }
        return new String(key);
    }

}
