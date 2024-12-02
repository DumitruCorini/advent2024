package com.advent.day1.service;

import com.advent.utils.service.FileUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class LocationManagerService {

    public Integer getLocationIdsDistanceFromTextBlock(String locationIdsInput) {
        List<String> locationIdsLines = locationIdsInput.lines().toList();
        return getLocationIdsDistance(locationIdsLines);
    }

    public Integer getLocationIdsDistanceFromFile(String fileName) {
        return getLocationIdsDistance(FileUtils.readFileAndGetLines(fileName));
    }

    public Integer getSimilarityScoreFromTextBlock(String inputLists) {
        return getSimilarityScore(inputLists.lines().toList());
    }

    public Integer getSimilarityScoreFromFile(String fileName) {
        return getSimilarityScore(FileUtils.readFileAndGetLines(fileName));
    }

    private List<List<Integer>> computeLists(List<String> locationIdsLines) {
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        locationIdsLines.forEach(
                line -> {
                    String[] splitLine = line.split("   ");
                    firstList.add(Integer.valueOf(splitLine[0]));
                    secondList.add(Integer.valueOf(splitLine[1]));
                }
        );

        return new ArrayList<>(Arrays.asList(firstList, secondList));
    }

    private Integer getLocationIdsDistance(List<String> locationIdsLines) {
        List<List<Integer>> computedLists = computeLists(locationIdsLines);
        List<Integer> firstList = computedLists.get(0);
        List<Integer> secondList = computedLists.get(1);

        Collections.sort(firstList);
        Collections.sort(secondList);

        int locationIdsTotalDistance = 0;

        for (int listIdx = 0; listIdx < firstList.size(); listIdx++) {
            Integer firstLocationId = firstList.get(listIdx);
            Integer secondLocationId = secondList.get(listIdx);
            locationIdsTotalDistance += Math.abs(firstLocationId - secondLocationId);
        }

        return locationIdsTotalDistance;
    }

    private int getSimilarityScore(List<String> inputLists) {
        int similarityScore = 0;

        List<List<Integer>> computedLists = computeLists(inputLists);
        List<Integer> firstList = computedLists.get(0);
        List<Integer> secondList = computedLists.get(1);

        for (Integer locationId : firstList) {
            int locationIdFrequency = Collections.frequency(secondList, locationId);

            similarityScore += locationIdFrequency * locationId;
        }

        return similarityScore;
    }
}
