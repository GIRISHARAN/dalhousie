package com.amazon.dynamodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;

import java.util.HashMap;
import java.util.Map;

public class DeleteItem {
    public static boolean deleteDynamoItem(String deleteRequestBody) {

        try {

            System.out.printf("Requested Delete Bode %n%s", deleteRequestBody);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(deleteRequestBody);
            String uuidKey = jsonNode.get("uuid").asText();
            String questionLevel = jsonNode.get("questionLevel").asText();

            String tableName = "QuizQuestions";

            // Specify the region for the bucket
            Region selectedRegion = Region.US_EAST_1;

            // Code to select a particular profile present in the .aws/credential file
            ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder().profileName("accessed-July-12-2023").build();

            DynamoDbClient dynamoDbClient = DynamoDbClient.builder().credentialsProvider(credentialsProvider).build();

            Map<String, AttributeValue> keyValues = new HashMap<>();
            keyValues.put("uuidKey", AttributeValue.builder().s(uuidKey).build());
            keyValues.put("questionLevel", AttributeValue.builder().s(questionLevel).build());

            // Build the DeleteItemRequest to delete the item with the specified UUID key and question level
            DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                    .tableName(tableName)
                    .key(keyValues)
                    .build();

            // Delete the item from the DynamoDB table
            DeleteItemResponse response = dynamoDbClient.deleteItem(deleteItemRequest);
            System.out.println(response.toString());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
