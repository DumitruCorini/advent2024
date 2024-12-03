package com.advent.day3.component;

import com.advent.day3.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private MultiplicationService multiplicationService;

    @Override
    public void run(String... args) {
        // Part 2 (part 1 was modified)
        String fileName = "input.txt";
        int multiplicationResult = multiplicationService.getMemoryMultiplicationResultFromFile(fileName);
        System.out.println("Multiplication result: " + multiplicationResult);
    }
}
