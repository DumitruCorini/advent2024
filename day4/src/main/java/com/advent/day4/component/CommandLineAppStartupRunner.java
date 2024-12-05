package com.advent.day4.component;

import com.advent.day4.service.CrosswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private CrosswordService crosswordService;

    @Override
    public void run(String... args) {
        // Part 1
        String fileName = "input.txt";
        int numberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrosswordFromFile(fileName);
        System.out.println("Number of XMAS: " + numberOfXMASInCrossword);

        // Part 2
        int numberOfCrossedMASInCrossword = crosswordService.getNumberOfCrossedMASInCrosswordFile(fileName);
        System.out.println("Number of crossed MAS: " + numberOfCrossedMASInCrossword);
    }
}
