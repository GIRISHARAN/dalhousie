package docker.container;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileOperations {

    public static String storeDataToFile(String fileName, String data) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        if (fileName == null) {

            FileResponse response = new FileResponse(fileName, "Invalid JSON input.", null, null);
            return objectMapper.writeValueAsString(response);
        }

        String filePath = "src/pusuluru_PV_dir/" + fileName;


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