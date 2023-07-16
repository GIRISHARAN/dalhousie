package com.amazon.dynamodb;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamoDB_Add {
    public static void addQuestionDB(QuestionInput questionInput) {

        System.out.println("Coming Here in addQuestionDB");

        List<String> options = questionInput.getOptions();
        System.out.println(options.toString());
        String tableName = "QuizQuestions";

        // Specify the region for the bucket
        Region selectedRegion = Region.US_EAST_1;

        // Code to select a particular profile present in the .aws/credential file
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder().profileName("accessed-July-13-2023").build();

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder().credentialsProvider(credentialsProvider).build();

        Map<String, AttributeValue> itemValues = new HashMap<>();

        itemValues.put("uuidKey", AttributeValue.builder().s(UUID_Key_Generate.generateUUIDKey().toString()).build());
        itemValues.put("questionLevel", AttributeValue.builder().s(questionInput.getQuestionLevel()).build());
        itemValues.put("question", AttributeValue.builder().s(questionInput.getQuestion()).build());
        itemValues.put("correctAnswer", AttributeValue.builder().s(questionInput.getCorrectAnswer()).build());

        /*List<AttributeValue> optionValues = options.stream()
                .map(option -> AttributeValue.builder().s(option).build())
                .collect(Collectors.toList());
        itemValues.put("options", AttributeValue.builder().l(optionValues).build());*/

        List<AttributeValue> optionValues = options.stream()
                .map(option -> AttributeValue.builder().s(option).build())
                .collect(Collectors.toList());
        itemValues.put("options", AttributeValue.builder().ss(optionValues.stream().map(AttributeValue::s).collect(Collectors.toList())).build());


        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("QuizQuestions")
                .item(itemValues)
                .build();

        dynamoDbClient.putItem(putItemRequest);

    }
}