package szfm.errorbynight.services;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    void store(MultipartFile file, String imageName) throws FileUploadException;

    Resource loadAsResource(String filename);

    Path loadPath(String fileName);
}
