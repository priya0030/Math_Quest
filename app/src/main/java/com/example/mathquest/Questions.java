package com.example.mathquest;

import java.util.*;

public class Questions {

    public static Question getQuestion(List<String> selectedSigns, String level) {
        if (selectedSigns == null || selectedSigns.isEmpty()) {
            selectedSigns = List.of("+", "-", "*", "/");
        }

        Random random = new Random();
        String selectedSign = selectedSigns.get(random.nextInt(selectedSigns.size()));

        int min = 1, max = 10; // default for easy
        switch (level.toLowerCase()) {
            case "medium":
                min = 10;
                max = 50;
                break;
            case "hard":
                min = 50;
                max = 100;
                break;
            case "complex":
                min = 100;
                max = 999;
                break;
        }

        if (selectedSign.equals("*")) {
            switch (level.toLowerCase()) {
                case "easy": min = 0; max = 10; break;
                case "medium": min = 1; max = 15; break;
                case "hard": min = 10; max = 25; break;
                case "complex": min = 10; max = 50; break;
            }
        }

        int num1 = getRandomInRange(min, max);
        int num2 = getRandomInRange(min, max);
        int result = 0;

        switch (selectedSign) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                while (num1 < num2) {
                    num1 = getRandomInRange(min, max);
                    num2 = getRandomInRange(min, max);
                }
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                do {
                    num2 = getRandomInRange(1, max); // avoid 0
                    num1 = num2 * getRandomInRange(1, max / num2); // ensure divisible
                } while (num2 == 0);
                result = num1 / num2;
                break;
        }

        List<Integer> options = generateOptions(result, random);
        return new Question(num1, num2, selectedSign, result, false, level, options);
    }

    private static int getRandomInRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private static List<Integer> generateOptions(int correctAnswer, Random random) {
        Set<Integer> optionsSet = new HashSet<>();
        optionsSet.add(correctAnswer);

        while (optionsSet.size() < 3) {
            int offset = random.nextInt(10) + 1; // small error margin
            int wrongAnswer = correctAnswer + (random.nextBoolean() ? offset : -offset);
            if (wrongAnswer != correctAnswer && wrongAnswer >= 0) {
                optionsSet.add(wrongAnswer);
            }
        }

        List<Integer> options = new ArrayList<>(optionsSet);
        Collections.shuffle(options);
        return options;
    }
}

