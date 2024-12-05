package com.advent.day5.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PrintingServiceTest {

    @Autowired
    private PrintingService printingService;

    @Test
    void should_return_5_when_we_have_single_page_ordering_rule_without_effect_and_one_printing_pages_suite_to_produce_1_5_2_in_a_text_block() {
        // GIVEN
        String printingOrders = """
                10|9
                
                1,5,2
                """;
        int expectedUpdatesSum = 5;

        // WHEN
        int actualUpdatesSum = printingService.getSumOfCorrectUpdates(printingOrders.lines().toList());

        // THEN
        assertEquals(expectedUpdatesSum, actualUpdatesSum);
    }

    @Test
    void should_return_143_updates_sum_for_text_file_inputTest() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedUpdatesSum = 143;

        // WHEN
        int actualUpdatesSum = printingService.getSumOfCorrectUpdatesFromFile(fileName);

        // THEN
        assertEquals(expectedUpdatesSum, actualUpdatesSum);
    }
}
