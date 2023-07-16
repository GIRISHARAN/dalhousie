package com.amazon.dynamodb;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DynamoDB {
    public static List<QuestionInput> getDynamoDBConnection(String questionLevel) {

        System.out.println(questionLevel);

        String tableName = "QuizQuestions";

        // Specify the region for the bucket
        Region selectedRegion = Region.US_EAST_1;

        // Code to select a particular profile present in the .aws/credential file
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder().profileName("accessed-July-13-2023").build();

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder().credentialsProvider(credentialsProvider).build();

        // Using ScanRequest
        // Create a ScanRequest specifying the table name and filter expression
        Map<String, AttributeValue> expressionAttributeValues = Map.of(":skValue", AttributeValue.builder().s(questionLevel).build());

        ScanRequest scanRequest = ScanRequest.builder().tableName("QuizQuestions").filterExpression("#sk = :skValue").expressionAttributeNames(Map.of("#sk", "questionLevel")).expressionAttributeValues(expressionAttributeValues).build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        List<QuestionInput> availableQuestions = new ArrayList<>();

        // Iterate over the items and print the desired information
        for (Map<String, AttributeValue> item : scanResponse.items()) {
            String uuidKey = item.get("uuidKey").s();
            String questionLevelGot = item.get("questionLevel").s();
            String question = item.get("question").s();
            List<String> options = item.get("options").ss();
            String correctAnswer = item.get("correctAnswer").s();

            String output = uuidKey + " - " + questionLevelGot + " - " + question + " - " + options + " - " + correctAnswer;
            System.out.println(output);

            QuestionInput questionInput = new QuestionInput(uuidKey, questionLevel, question, correctAnswer, options);

            availableQuestions.add(questionInput);

        }

        // close the connection
        dynamoDbClient.close();

        return availableQuestions;
    }
}