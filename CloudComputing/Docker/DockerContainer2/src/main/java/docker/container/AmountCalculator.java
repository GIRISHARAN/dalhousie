package docker.container;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AmountCalculator {
    public static int calculateTotalAmount(String filePath, String productName) throws IOException {
        int totalAmount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] values = line.strip().split(",");
                if (values.length == 2) {
                    String product = values[0].strip();
                    int amount;
                    try {
                        amount = Math.abs(Integer.parseInt(values[1].strip().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount in the file.");
                        return -1;
                    }
                    if (product.equalsIgnoreCase(productName)) {
                        totalAmount += amount;
                    }
                } else {
                    System.out.println("File content is not in CSV format.");
                    return -1;
                }
            }
        }
        return totalAmount;
    }
}
