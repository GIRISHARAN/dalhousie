package docker.container;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSV_Validator {
    public static boolean isCSV(String filePath) throws IOException {
        System.out.println("Coming into CSV_Validator");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            System.out.println("Line in Controller"+line);
            String[] values = line.strip().split(",");
            if (values[0].toLowerCase().strip().equals("product") && values[1].toLowerCase().strip().equals("amount")) {
                while ((line = reader.readLine()) != null) {
                    if (!line.strip().contains(",")) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}