package service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class JarController {

    @GetMapping("/fetchJar")
    public ResponseEntity<InputStreamResource> fetchJarLogic() throws FileNotFoundException {

        return getInputStreamResourceResponseEntity("Service/jars/dependency-0.0.1.jar") ;
    }

    private ResponseEntity<InputStreamResource> getInputStreamResourceResponseEntity(String f) throws FileNotFoundException {
        File downloadFile = new File( f );

        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));

        HttpHeaders headers = new HttpHeaders();

        String contentDispositionValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        System.out.println("content disposition value for >" + f + "< is >" + contentDispositionValue + "<");
        headers.add("Content-Disposition", contentDispositionValue);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<InputStreamResource> inputStreamResourceResponseEntity = ResponseEntity.ok().
                headers(headers)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);

        return inputStreamResourceResponseEntity;
    }


}
