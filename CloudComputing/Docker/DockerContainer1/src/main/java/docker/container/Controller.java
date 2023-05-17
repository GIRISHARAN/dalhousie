package docker.container;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {
    @PostMapping("/calculate")
    public ResponseEntity<String> calculate(@RequestBody InputData inputData) throws IOException {
        String fileName = inputData.getFile();
        String productName = inputData.getProduct();

        System.out.println("ABC-FileName-"+fileName+"-Product Name-"+productName);

        String validationResult = FileValidator.validateFileName(fileName, productName);
        System.out.println(validationResult);

        Map<String, Object> response = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        
        String responseBody = objectMapper.writeValueAsString(response);

        return ResponseEntity.ok().body(responseBody);
    }
}