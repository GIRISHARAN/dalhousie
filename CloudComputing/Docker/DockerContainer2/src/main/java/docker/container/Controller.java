package docker.container;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/performCalculation")
    public CalculationResponse calculateValue(@RequestParam("file") String fileName,
            @RequestParam("product") String productName) throws IOException {

        String filePath = "/app/volume/" + fileName;

        // String filePath =
        // "C:\\Users\\AVuser\\Desktop\\GiriSharanReddy\\dalhousie\\CloudComputing\\Docker\\"
        // + fileName;

        if (CSV_Validator.isCSV(filePath)) {
            int totalAmount = AmountCalculator.calculateTotalAmount(filePath, productName);
            CalculationResponse response = new CalculationResponse(fileName, totalAmount);
            System.out.println("In COntainer2" + response);
            return response;
        }
        CalculationResponse response = new CalculationResponse(fileName,
                "Input file not in CSV format.");
        System.out.println("In COntainer2" + response);
        return response;
    }
}