package amazon.s3bucket;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

public class App {

    /*[1]	Amazon.com. [Online].
        Available: https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html. [Accessed: 22-Jul-2023].*/

    public static void main(String[] args) {

        final String sourcePath = "C:\\Users\\AVuser\\Downloads\\tech\\";

        // Specify the name of the bucket
        final String bucketName = "sampledata-b00913674";

        // Specify the region for the bucket
        Region selectedRegion = Region.US_EAST_1;

        // Code to select a particular profile present in the .aws/credential file
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder().profileName("accessed-June-22-2023").build();

        // Create an S3 client using the specified region and credentials
        S3Client s3Client = S3Client.builder().region(selectedRegion).credentialsProvider(credentialsProvider).build();

        // Call a method to upload a file to the created bucket
        FileOperation.uploadFiles(bucketName, s3Client, sourcePath);
    }
}