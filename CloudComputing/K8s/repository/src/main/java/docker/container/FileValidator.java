package docker.container;

import java.io.File;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileValidator {

    public static String validateFileName(String fileName, String productName) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        if (fileName == null) {

            FileResponse response = new FileResponse(fileName, "Invalid JSON input.", null, null);
            return objectMapper.writeValueAsString(response);
        }

        String filePath = "src/pusuluru_PV_dir/" + fileName;

        File file = new File(filePath);

        if (file.exists()) {

            String container2URL = ("http://localhost:8080/performCalculation");

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(container2URL)
                    .queryParam("file", fileName)
                    .queryParam("product", productName);
            String urlWithParams = uriBuilder.toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlWithParams, String.class);

            String responseBody = responseEntity.getBody();

            System.out.println("Response Body: " + responseBody);

            return responseBody;
        } else {

            FileResponse response = new FileResponse(fileName, "File not found.", null, null);
            return objectMapper.writeValueAsString(response);
        }
    }
}