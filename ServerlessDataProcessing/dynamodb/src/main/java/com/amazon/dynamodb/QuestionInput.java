package com.amazon.dynamodb;

import java.util.List;

public class QuestionInput {
    private String uuid;
    private String questionLevel;
    private String question;
    private String correctAnswer;
    private List<String> options;

    public QuestionInput() {
    }

    public QuestionInput(String uuid, String questionLevel, String question, String correctAnswer, List<String> options) {
        this.uuid = uuid;
        this.questionLevel = questionLevel;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
