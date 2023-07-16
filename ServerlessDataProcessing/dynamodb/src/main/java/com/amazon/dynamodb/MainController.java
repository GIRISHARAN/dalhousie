package com.amazon.dynamodb;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MainController {
    @GetMapping("/dynamo")
    public ResponseEntity<List<QuestionInput>> getDynamo(@RequestParam String questionLevel) {
        List<QuestionInput> questions = DynamoDB.getDynamoDBConnection(questionLevel);
        return ResponseEntity.ok(questions);
    }


    @PostMapping("/add-question")
    public String newQuestion(@RequestBody QuestionInput questionInput) {
        System.out.println(questionInput.toString());
        System.out.println(questionInput.getQuestion());
        System.out.println(questionInput.getQuestionLevel());
        System.out.println(questionInput.getUuid());
        System.out.println(questionInput.getCorrectAnswer());
        System.out.println(questionInput.getOptions().toString());

        DynamoDB_Add.addQuestionDB(questionInput);
        return "Question added";
    }

    @PostMapping("/deleteQuestion")
    public ResponseEntity<Boolean> deleteQuestion(@RequestBody String deleteRequestBody) {
        boolean deleted = DeleteItem.deleteDynamoItem(deleteRequestBody);
        return ResponseEntity.ok(deleted);
    }
}