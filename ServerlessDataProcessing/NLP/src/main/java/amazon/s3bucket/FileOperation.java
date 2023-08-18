package amazon.s3bucket;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class FileOperation {


    /*[1]	Amazon.com. [Online].
        Available: https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html. [Accessed: 22-Jul-2023].*/

    /**
     * Uploads multiple files to the specified S3 bucket with a 100ms delay between uploads.
     * Adds a timestamp in milliseconds when each file is uploaded.
     *
     * @param bucketName the name of the S3 bucket
     * @param s3Client   the S3 client used for the upload
     * @param sourcePath the local directory path containing the files to upload
     */
    public static void uploadFiles(String bucketName, S3Client s3Client, String sourcePath) {
        // Get the list of files in the source directory
        File directory = new File(sourcePath);
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No files found in the specified directory: " + sourcePath);
            return;
        }

        // Iterate over the files and upload each file with a 100ms delay between uploads
        for (File file : files) {
            // Check if the item is a file (not a directory)
            if (file.isFile()) {
                // Specify the name of the file to upload
                String fileName = file.getName();

                // Create a request to upload the file to the specified bucket with the specified key (file name)
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();

                // Get the current timestamp
                Instant timestamp = Instant.now();

                // Upload the file to the S3 bucket using the S3 client and request
                PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

                // Format the timestamp using the desired format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                String formattedTimestamp = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault())
                        .format(formatter);

                // Print the response from the upload operation along with the formatted timestamp
                System.out.println("Uploaded file: " + fileName + ", ETag: " + putObjectResponse.eTag()
                        + ", Timestamp: " + formattedTimestamp);

                try {
                    // Sleep for 100ms before uploading the next file
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}