package docker.container;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/getValue")
    public String getValue() {
        System.out.println("Hello World");
        return "hello World";
    }
}
