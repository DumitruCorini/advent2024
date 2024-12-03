package com.advent.day3.service;

import com.advent.utils.service.FileUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MultiplicationService {

    boolean activeMulInstructions = true;

    public int getMemoryMultiplicationResult(String memory) {
        int multiplicationsTotalResult = 0;
        Matcher matcher = Pattern.compile("(do\\(\\))|(mul\\([0-9]{1,3},[0-9]{1,3}\\))|(don't\\(\\))").matcher(memory);

        while (matcher.find()) {
            String foundMatch = matcher.group();

            if (foundMatch.startsWith("don't")) {
                activeMulInstructions = false;
            } else if (foundMatch.startsWith("do")) {
                activeMulInstructions = true;
            } else if (foundMatch.startsWith("mul") && activeMulInstructions) {
                // Remove 'mul('
                String foundMatchWithoutMulKeyword = foundMatch.substring(4);
                // Remove ')' to keep only the numbers to multiply
                String numbersToModifySeparatedByCommaChar = foundMatchWithoutMulKeyword.substring(0, foundMatchWithoutMulKeyword.length() - 1);
                // Split numbers by comma char
                String[] numbersToMultiply = numbersToModifySeparatedByCommaChar.split(",");
                // Multiply the two numbers and add it to result
                multiplicationsTotalResult += Integer.parseInt(numbersToMultiply[0]) * Integer.parseInt(numbersToMultiply[1]);
            }
        }

        return multiplicationsTotalResult;
    }

    public int getMemoryMultiplicationResultFromFile(String fileName) {
        int multiplicationTotalResult = 0;
        List<String> fileLines = FileUtils.readFileAndGetLines(fileName);

        for (String fileLine : fileLines) {
            multiplicationTotalResult += getMemoryMultiplicationResult(fileLine);
        }

        return multiplicationTotalResult;
    }
}
