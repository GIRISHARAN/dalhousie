package amazon.s3bucket;

import java.io.File;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class FileOperation {

    /**
     * Uploads a file to the specified S3 bucket.
     *
     * @param bucketName the name of the S3 bucket
     * @param s3Client   the S3 client used for the upload
     */

    /**
     * This method is based on the code provided by Amazon Web Services:
     * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
     */
    public static void uploadFile(String bucketName, S3Client s3Client, String sourcePath) {

        // Specify the name of the file to upload
        final String fileName = "index.html";

        // Specify the local file path of the file to upload
        String filePath = sourcePath + fileName;

        // Create a File object from the specified file path
        File file = new File(filePath);

        // Check if the file exists locally
        if (file.exists()) {
            System.out.println("File Exists in Local");
        }

        // Create a request to upload the file to the specified bucket with the specified key (file name)
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        // Upload the file to the S3 bucket using the S3 client and request
        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

        // Print the response from the upload operation
        System.out.println(putObjectResponse.toString());
    }
}