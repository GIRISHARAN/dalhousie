package docker.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FileValidator {
    public static String validateFileName(String fileName, String productName) throws IOException {
        if (fileName == null) {
            return "Invalid JSON input.";
        } else if (!fileName.equals("file.dat")) {
            return "File not found.";
        } else {
            URL url = new URL("http://localhost:7000/getValue");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            String response;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                response = responseBuilder.toString();
            }

            // Process the response from Application B
            System.out.println("Value from Application B: " + responseCode + " - " + response);
        }

        return null;
    }
}
