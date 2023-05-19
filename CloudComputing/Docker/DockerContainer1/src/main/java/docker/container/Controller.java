package docker.container;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping("/calculate")
    public ResponseEntity<String> calculate(@RequestBody InputData inputData) throws IOException {
        String fileName = inputData.getFile();
        String productName = inputData.getProduct();

        String validationResult = FileValidator.validateFileName(fileName, productName);
        System.out.println(validationResult);

        return ResponseEntity.ok().body(validationResult);
    }
}