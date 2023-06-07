package amazon.s3bucket;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;

public class App {

    /**
     * This method is based on the code provided by Amazon Web Services:
     * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
     */
    public static void main(String[] args) {

        final String sourcePath = "C:\\Users\\AVuser\\Desktop\\GiriSharanReddy\\dalhousie\\ServerlessDataProcessing\\AmazonWebServices\\";

        // Specify the name of the bucket
        final String bucketName = "girisharan-s3bucket-serverless";

        // Specify the region for the bucket
        Region selectedRegion = Region.US_EAST_1;

        // Code to select a particular profile present in the .aws/credential file
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder()
                .profileName("accessed-June-06-2023-2")
                .build();

        // Create an S3 client using the specified region and credentials
        S3Client s3Client = S3Client.builder()
                .region(selectedRegion)
                .credentialsProvider(credentialsProvider)
                .build();

        // Create a request to create a bucket with the specified name
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        try {
            // Attempt to create the bucket
            CreateBucketResponse createBucketResponse = s3Client.createBucket(createBucketRequest);
            System.out.println(createBucketResponse.toString());

            // Call a method to upload a file to the created bucket
            FileOperation.uploadFile(bucketName, s3Client, sourcePath);

        } catch (BucketAlreadyExistsException bucketExistsException) {
            // Handle the case when the bucket already exists
            System.out.println("Bucket already exists: " + bucketName);
        } catch (BucketAlreadyOwnedByYouException bucketOwnedByYouException) {
            // Handle the case when the bucket is already owned by you
            System.out.println("Bucket already owned by you: " + bucketName);
        }
    }
}