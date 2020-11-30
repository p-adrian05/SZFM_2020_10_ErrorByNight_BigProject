package szfm.errorbynight.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import szfm.errorbynight.util.UtilService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
  private final Path rootLocation;

  public StorageServiceImpl(){
        this.rootLocation = Paths.get("user-images");
        init();
  }

  @Override
  public void init() {
    try{
      Files.createDirectories(rootLocation);
    }catch (IOException e){
      log.error(e.getMessage());
    }
  }

  @Override
  public void store(MultipartFile file, String imageName) throws FileUploadException {
    try{
      if(!file.isEmpty()){
        UtilService.validateUploadedProfileImage(file);
        Files.copy(file.getInputStream(),this.rootLocation.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
      }
    }
    catch (IOException e){
      throw new FileUploadException(e.getMessage());
    }
  }

  @Override
  public Resource loadAsResource(String filename) {

  }

  @Override
  public Path loadPath(String fileName) {

  }
}
