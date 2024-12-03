package com.advent.day2.component;

import com.advent.day2.service.ReportSafetyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private ReportSafetyService reportSafetyService;

    @Override
    public void run(String... args) {
        // Part 1
        String fileName = "input.txt";
        int numberOfSafeReportsFromFile = reportSafetyService.getNumberOfSafeReportsFromFile(fileName);
        System.out.println("Number of safe reports: " + numberOfSafeReportsFromFile);

        // Part 2
        int numberOfSafeReportsFromFileWhileUsingProblemDampener = reportSafetyService.getNumberOfSafeReportsFromFileWhileUsingProblemDampener(fileName);
        System.out.println("Number of safe reports when using problem dampener: " + numberOfSafeReportsFromFileWhileUsingProblemDampener);
    }
}
