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

            FileResponse response = new FileResponse(fileName,
                    "Input file not in CSV format.");

            return objectMapper.writeValueAsString(response);
        }

        String filePath = "/app/volume/" + fileName;

        // String filePath =
        // "C:\\Users\\AVuser\\Desktop\\GiriSharanReddy\\dalhousie\\CloudComputing\\Docker\\"
        // + fileName;

        File file = new File(filePath);

        if (file.exists()) {

            String container2URL = ("http://docker-container2-container:7000/performCalculation");

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

            FileResponse response = new FileResponse(fileName, "File not found.");
            return objectMapper.writeValueAsString(response);
        }
    }
}