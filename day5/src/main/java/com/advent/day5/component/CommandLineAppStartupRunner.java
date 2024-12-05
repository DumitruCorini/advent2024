package com.advent.day5.component;

import com.advent.day5.service.PrintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private PrintingService printingService;

    @Override
    public void run(String... args) {
        // Part 1
        String fileName = "input.txt";
        int sumOfCorrectUpdatesFromFile = printingService.getSumOfCorrectUpdatesFromFile(fileName);
        System.out.println("Sum of correct updates from file: " + sumOfCorrectUpdatesFromFile);

    }
}
