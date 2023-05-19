package docker.container;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSV_Validator {
    public static boolean isCSV(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(",")) {
                    return false;
                }
            }
        }
        return true;
    }
}
