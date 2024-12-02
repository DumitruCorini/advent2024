package com.advent.day1.component;

import com.advent.day1.service.LocationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    LocationManagerService locationManagerService;

    @Override
    public void run(String... args) {
        // Part 1
        String fileName = "input.txt";
        Integer locationIdsDistance = locationManagerService.getLocationIdsDistanceFromFile(fileName);
        System.out.println("Location ids distance: " + locationIdsDistance);

        // Part 2
        Integer locationIdSimilarity = locationManagerService.getSimilarityScoreFromFile(fileName);
        System.out.println("Location ids similarity score: " + locationIdSimilarity);
    }
}
