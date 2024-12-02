package com.advent.day1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LocationManagerServiceTest {

    @Autowired
    private LocationManagerService locationManagerService;

    // LocationIds total distance score:
    // Sum of distances grouped by value
    // location ids come from lists that are in a file:
    // Ex: 3   2
    //     1   5
    // min from first list is grouped with min from the second list, second min with second min, and so on
    @Test
    void should_get_distance_score_4_for_text_block_with_first_list_1_and_second_list_5() {
        // GIVEN
        String locationIdsInput = """
                1   5
                """;
        Integer expectedDistanceScore = 4;

        // WHEN
        Integer actualDistanceScore = locationManagerService.getLocationIdsDistanceFromTextBlock(locationIdsInput);

        // THEN
        assertEquals(expectedDistanceScore, actualDistanceScore);
    }

    @Test
    void should_get_distance_score_3_for_text_block_with_first_list_3_1_and_second_list_2_5() {
        // GIVEN
        String locationIdsInput = """
                3   2
                1   5
                """;
        Integer expectedDistanceScore = 3;

        // WHEN
        Integer actualDistanceScore = locationManagerService.getLocationIdsDistanceFromTextBlock(locationIdsInput);

        // THEN
        assertEquals(expectedDistanceScore, actualDistanceScore);
    }

    @Test
    void should_read_inputTest_text_file_and_get_locationIdsDistance_11() {
        // GIVEN
        String fileName = "inputTest.txt";
        Integer expectedLocationIdsDistance = 11;

        // WHEN
        Integer actualLocationIdsDistance = locationManagerService.getLocationIdsDistanceFromFile(fileName);

        // THEN
        assertEquals(expectedLocationIdsDistance, actualLocationIdsDistance);
    }

    // Similarity score is:
    // the sum of: number of occurrences for an locationId from first list in second one * (times) the value of the locationId
    // For example, for lists 
    //  2   2 
    // 2 from first list appears once, so:
    // 1 (number of occurrences) * 2 (value)
    @Test
    void should_get_similarity_score_2_for_text_block_with_first_list_2_and_second_list_2() {
        // GIVEN
        String inputLists = """
                2   2
                """;
        Integer expectedSimilarityScore = 2;

        // WHEN
        Integer actualSimilarityScore = locationManagerService.getSimilarityScoreFromTextBlock(inputLists);

        // THEN
        assertEquals(expectedSimilarityScore, actualSimilarityScore);
    }

    @Test
    void should_get_similarity_score_10_for_text_block_with_first_list_2_5_5_and_second_list_5_1_3() {
        // GIVEN
        String inputLists = """
                2   5
                5   1
                5   3
                """;
        Integer expectedSimilarityScore = 10;

        // WHEN
        Integer actualSimilarityScore = locationManagerService.getSimilarityScoreFromTextBlock(inputLists);

        // THEN
        assertEquals(expectedSimilarityScore, actualSimilarityScore);
    }

    @Test
    void should_get_similarity_score_31_for_text_file_inputTest() {
        // GIVEN
        String fileName = "inputTest.txt";
        Integer expectedSimilarityScore = 31;

        // WHEN
        Integer actualSimilarityScore = locationManagerService.getSimilarityScoreFromFile(fileName);

        // THEN
        assertEquals(expectedSimilarityScore, actualSimilarityScore);
    }
}
