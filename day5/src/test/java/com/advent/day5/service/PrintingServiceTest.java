package com.advent.day5.service;

import com.advent.day5.model.PageOrderingRule;
import com.advent.utils.service.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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
    void should_return_143_correct_updates_sum_for_text_file_inputTest() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedUpdatesSum = 143;

        // WHEN
        int actualUpdatesSum = printingService.getSumOfCorrectUpdatesFromFile(fileName);

        // THEN
        assertEquals(expectedUpdatesSum, actualUpdatesSum);
    }

    @Test
    void should_return_0_when_trying_to_get_incorrect_updates_sum_with_page_ordering_rule_10_9_and_single_correct_printing_instruction_1_5_2_in_text_block() {
        // GIVEN
        String printingOrders = """
                10|9
                
                1,5,2
                """;
        int expectedIncorrectUpdatesSum = 0;

        // WHEN
        int actualIncorrectUpdatesSum = printingService.getSumOfIncorrectUpdates(printingOrders.lines().toList());

        // THEN
        assertEquals(expectedIncorrectUpdatesSum, actualIncorrectUpdatesSum);
    }

    @Test
    void should_return_9_when_trying_to_get_incorrect_updates_sum_with_page_ordering_rule_9_10_and_single_incorrect_printing_instruction_5_10_9_in_text_block() {
        // GIVEN
        String printingOrders = """
                9|10
                
                5,10,9
                """;
        int expectedIncorrectUpdatesSum = 9;

        // WHEN
        int actualIncorrectUpdatesSum = printingService.getSumOfIncorrectUpdates(printingOrders.lines().toList());

        // THEN
        assertEquals(expectedIncorrectUpdatesSum, actualIncorrectUpdatesSum);
    }

    @Test
    void should_return_19_when_trying_to_get_incorrect_updates_sum_with_page_ordering_rule_9_10_and_incorrect_printing_instructions_5_10_9_and_2_10_3_1_9_in_text_block() {
        // GIVEN
        String printingOrders = """
                9|10
                
                5,10,9
                2,10,3,1,9
                """;
        int expectedIncorrectUpdatesSum = 19;

        // WHEN
        int actualIncorrectUpdatesSum = printingService.getSumOfIncorrectUpdates(printingOrders.lines().toList());

        // THEN
        assertEquals(expectedIncorrectUpdatesSum, actualIncorrectUpdatesSum);
    }

    @Test
    void should_return_19_when_trying_to_get_incorrect_updates_sum_with_page_ordering_rule_9_10_and_incorrect_printing_instructions_5_10_9_and_2_10_3_1_9_and_correct_instruction_1_9_10_in_text_block() {
        // GIVEN
        String printingOrders = """
                9|10
                
                5,10,9
                2,10,3,1,9
                1,9,10
                """;
        int expectedIncorrectUpdatesSum = 19;

        // WHEN
        int actualIncorrectUpdatesSum = printingService.getSumOfIncorrectUpdates(printingOrders.lines().toList());

        // THEN
        assertEquals(expectedIncorrectUpdatesSum, actualIncorrectUpdatesSum);
    }

    @Test
    void should_return_123_incorrect_updates_sum_for_text_file_inputTest() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedUpdatesSum = 123;

        // WHEN
        int actualUpdatesSum = printingService.getSumOfIncorrectUpdatesFromFile(fileName);

        // THEN
        assertEquals(expectedUpdatesSum, actualUpdatesSum);
    }

    @Test
    void NFR_should_reorder_79_67_97_29_47_96_45_25_44_55_43_76_77_to_55_97_29_76_77_43_47_67_25_96_45_44_79_for_text_file_inputTest2() {
        // GIVEN
        List<String> updateInstructions = FileUtils.readFileAndGetLines("inputTest2.txt");
        
        // Find where pageOrdering rules end and pages to print start
        int updateInstructionsSeparationIndex = updateInstructions.indexOf("");

        // Get page ordering rules from update instructions
        List<PageOrderingRule> pageOrderingRules = printingService.getPageOrderingRules(updateInstructions, updateInstructionsSeparationIndex);

        // Get update lists from update instructions
        List<List<Integer>> listOfPagesToPrint = printingService.createPagesToPrintList(updateInstructions, updateInstructionsSeparationIndex);

        List<Integer> expectedOrderedList = Arrays.asList(55, 97, 29, 76, 77, 43, 47, 67, 25, 96, 45, 44, 79);

        // WHEN
        List<Integer> actualUpdatesSum = printingService.reorderPagesToPrintToFollowPageOrderingRules(listOfPagesToPrint.getFirst(), pageOrderingRules);

        // THEN
        assertEquals(expectedOrderedList, actualUpdatesSum);
    }
}
