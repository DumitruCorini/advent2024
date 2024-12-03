package com.advent.day3.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MultiplicationServiceTest {

    @Autowired
    private MultiplicationService multiplicationService;

    @Test
    void should_get_multiplication_result_0_when_we_only_have_corrupted_memory() {
        // GIVEN
        String memory = "xf2o10/amul1p";
        int expectedMultResult = 0;

        // WHEN
        int actualMultResult = multiplicationService.getMemoryMultiplicationResult(memory);

        // THEN
        assertEquals(expectedMultResult, actualMultResult);
    }

    @Test
    void should_get_multiplication_result_2_when_we_have_single_mult_instruction_with_mult_1_2() {
        // GIVEN
        String memory = "1mul(1,2)/1mumulmult";
        int expectedMultResult = 2;

        // WHEN
        int actualMultResult = multiplicationService.getMemoryMultiplicationResult(memory);

        // THEN
        assertEquals(expectedMultResult, actualMultResult);
    }

    @Test
    void should_get_multiplication_result_161_when_we_have_multiple_mul_instructions_in_inputTest_text_file() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedMultResult = 161;

        // WHEN
        int actualMultResult = multiplicationService.getMemoryMultiplicationResultFromFile(fileName);

        // THEN
        assertEquals(expectedMultResult, actualMultResult);
    }

    @Test
    void should_return_4_when_we_have_multiple_mul_instructions_but_memory_starts_with_dont_and_afterwards_only_one_that_is_between_a_do_instruction_and_a_dont_instruction() {
        // GIVEN
        String memory = "don't()/1smul(5,2)mul(1)3a[)do()=1u8hnjc120submul(2,2)/a]don't()";
        int expectedMultResult = 4;

        // WHEN
        int actualMultResult = multiplicationService.getMemoryMultiplicationResult(memory);

        // THEN
        assertEquals(expectedMultResult, actualMultResult);
    }

    @Test
    void should_return_48_multiplication_total_result_from_text_file_inputTest2() {
        // GIVEN
        String fileName = "inputTest2.txt";
        int expectedMultResult = 48;

        // WHEN
        int actualMultResult = multiplicationService.getMemoryMultiplicationResultFromFile(fileName);

        // THEN
        assertEquals(expectedMultResult, actualMultResult);
    }
}
