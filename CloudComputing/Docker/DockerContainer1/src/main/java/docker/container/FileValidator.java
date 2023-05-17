package docker.container;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileValidator {
    public static String validateFileName(String fileName, String productName) throws IOException {

        System.out.println("File Name :" + fileName);
        if (fileName == null) {
            return "Invalid JSON input.";
        }

        String filePath = "/app/volume/" + fileName;
        System.out.println("File Path:" + filePath + "File Name" + fileName);

        File file = new File(filePath);

        if (file.exists()) {

            System.out.println("File exists!");

            URL url = new URL("http://docker-container2-container:7000/getValue");
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
            return response;
        }
        return "Check your input";
    }
}
