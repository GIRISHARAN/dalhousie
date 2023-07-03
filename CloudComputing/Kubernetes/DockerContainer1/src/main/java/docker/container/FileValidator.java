package docker.container;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileValidator {

    public static String isFileNameNull(String fileName, String data) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        if (fileName == null) {

            FileResponse response = new FileResponse(fileName, "Invalid JSON input.", null, null);

            return objectMapper.writeValueAsString(response);
        } else {
            String filePath = "C:\\Users\\AVuser\\Desktop\\GiriSharanReddy\\dalhousie\\CloudComputing\\Kubernetes\\File\\"
                    + fileName;
            File file = new File(filePath);
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(data);
                System.out.println("Content written to the file successfully.");
                FileResponse response = new FileResponse(fileName, null, null, "Success.");
                return objectMapper.writeValueAsString(response);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String validateFileName(String fileName, String productName) throws IOException {

            String container2URL = ("http://localhost:8001/performCalculation");
            // String container2URL = ("https://eog5ga5s9kzr4cb.m.pipedream.net");

            // Create the request body
            RequestBodyInput requestBodyInput = new RequestBodyInput(fileName, productName);

            // Create an instance of RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the RequestEntity with the request body and headers
            RequestEntity<RequestBodyInput> requestEntity = RequestEntity
                    .post(URI.create(container2URL))
                    .headers(headers)
                    .body(requestBodyInput);

            // Send the POST request
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    container2URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            // Retrieve the response body
            String response = responseEntity.getBody().toString();
            System.out.println("outputHere:\n"+response);
            return response;
        
    }
}