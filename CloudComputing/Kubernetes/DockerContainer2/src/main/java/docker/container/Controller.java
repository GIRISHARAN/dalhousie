package docker.container;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @PostMapping("/performCalculation")
    public CalculationResponse calculateValue(@RequestBody RequestBodyInput requestBodyInput) throws IOException {
        String fileName = requestBodyInput.getFileName();
        String productName = requestBodyInput.getProductName();
        System.out.println("fileName"+fileName);
        System.out.println("productName"+productName);
        if(fileName==null){
            CalculationResponse response = new CalculationResponse(fileName,
                    "Invalid JSON input.", null);
            return response;
        }

        String filePath = "C:\\Users\\AVuser\\Desktop\\GiriSharanReddy\\dalhousie\\CloudComputing\\Kubernetes\\File\\"
                + fileName;
        File file = new File(filePath);
        if(!file.exists()) {
            CalculationResponse response = new CalculationResponse(fileName,
                    "File not found.", null);
            return response;
        }
        if (CSV_Validator.isCSV(filePath)) {
            int totalAmount = AmountCalculator.calculateTotalAmount(filePath, productName);
            CalculationResponse response = new CalculationResponse(fileName,null, totalAmount);
            System.out.println("In COntainer2" + response);
            return response;
        }
        CalculationResponse response = new CalculationResponse(fileName,
                "Input file not in CSV format.", null);
        return response;
    }
}