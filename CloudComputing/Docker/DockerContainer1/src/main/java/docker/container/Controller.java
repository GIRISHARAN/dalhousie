package docker.container;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @PostMapping("/calculate")
    public String calculate(@RequestBody InputData inputData) throws IOException {
        String file = inputData.getFile();
        String product = inputData.getProduct();

        String validationResult = FileValidator.validateFileName(file, product);
        System.out.println(validationResult);

        return "Calculation complete"; // Placeholder response
    }
}
