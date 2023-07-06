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
        
        String filePath = "src/pusuluru_PV_dir/" + fileName;

        if (CSV_Validator.isCSV(filePath)) {
            String totalAmount = AmountCalculator.calculateTotalAmount(filePath, productName);
            CalculationResponse response = new CalculationResponse(fileName, totalAmount, null);
            System.out.println("In COntainer2" + response);
            return response;
        }
        CalculationResponse response = new CalculationResponse(fileName, null,
                "Input file not in CSV format.");
                // Temp Line
        System.out.println("In COntainer2" + response);
        return response;
    }
}