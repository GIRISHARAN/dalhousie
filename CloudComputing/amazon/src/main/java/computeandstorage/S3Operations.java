package computeandstorage;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;

public class S3Operations {

    // Specify the name of the bucket and Object
    final static String bucketName = "girisharan-s3bucket-serverless";
    final static String objectName = "index.html";

    // Specify the region for the bucket
    static Region selectedRegion = Region.US_EAST_1;

    static ProfileCredentialsProvider profileCredentialsProvider = ProfileCredentialsProvider.builder()
            .profileName("accessed-June-06-2023-2").build();

    static S3Client s3Client = S3Client.builder().region(selectedRegion).credentialsProvider(profileCredentialsProvider)
            .build();

    static S3Presigner s3Presigner = S3Presigner.builder().credentialsProvider(profileCredentialsProvider).region(selectedRegion).build();

    // Method to add Data
    public static String insertDataToS3Object(String requestData) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objectName).build();
        InputStream inputStream = s3Client.getObject(getObjectRequest);

        StringBuilder existingContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingContent.append(line);
            }
        } catch (IOException ioException) {
            return ioException.getMessage();
        }

        String updatedContent = existingContent.toString() + requestData;

        // Upload the updated content as a new object
        byte[] updatedContentBytes = updatedContent.getBytes();
        ByteArrayInputStream updatedContentStream = new ByteArrayInputStream(updatedContentBytes);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectName).build();
        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(updatedContentStream, updatedContent.length()));

        GetUrlRequest getUrlRequest = GetUrlRequest.builder().bucket(bucketName).key(objectName).build();
        URL objectURL = s3Client.utilities().getUrl(getUrlRequest);

        System.out.println("Object URL:" + objectURL);

        return objectURL.toString();
    }

    public static void deleteObject() {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(objectName)
                .build();
        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
//        generatePreSignedURL();
        System.out.println(deleteObjectResponse.toString());
    }

    /*public static String generatePreSignedURL() {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectName).build();
        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(5)).putObjectRequest(putObjectRequest).build();
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(putObjectPresignRequest);
        URL myURL = presignedRequest.url();
        System.out.println("Presigned URL to delete a file to: " + myURL);
        HttpURLConnection connection = null;
        try {

            connection = (HttpURLConnection) myURL.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestMethod("DELETE");
            *//*OutputStreamWriter out = null;
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text was uploaded as an object by using a presigned URL.");
            out.close();*//*
            connection.getResponseCode();
            System.out.println("HTTP response code is " + connection.getResponseCode());
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myURL.toString();
    }*/
}
