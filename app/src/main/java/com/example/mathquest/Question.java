package com.example.mathquest;

import java.util.List;

public class Question {
    int num1, num2;
    String operation, level;
    int correctAnswer;
    boolean isAnswered;
    List<Integer> options;

    public Question(int num1, int num2, String operation, int correctAnswer,
                    boolean isAnswered, String level, List<Integer> options) {
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
        this.correctAnswer = correctAnswer;
        this.isAnswered = isAnswered;
        this.level = level;
        this.options = options;
    }

    public String getQuestionText() {
        return num1 + " " + operation + " " + num2;
    }

}
