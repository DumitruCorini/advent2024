package com.advent.day2.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReportSafetyServiceTest {

    @Autowired
    private ReportSafetyService reportSafetyService;

    @ParameterizedTest
    @MethodSource(value = "getReportsWithSafetyStatus")
    void should_check_single_report_safety(String report, boolean expectedReportSafety) {
        // GIVEN

        // WHEN
        boolean actualReportSafety = reportSafetyService.isReportSafe(report);

        // THEN
        assertEquals(expectedReportSafety, actualReportSafety);
    }

    @Test
    void should_get_number_of_safe_reports_2_from_text_file_inputTest() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedSafeReportsNumber = 2;

        // WHEN
        int actualSafeReportsNumber = reportSafetyService.getNumberOfSafeReportsFromFile(fileName);

        // THEN
        assertEquals(expectedSafeReportsNumber, actualSafeReportsNumber);
    }

    @ParameterizedTest
    @MethodSource(value = "getReportsWithSafetyStatusWhenUsingProblemDampener")
    void should_detect_reports_safety_when_using_problem_dampener(String report, boolean expectedReportSafety) {
        // GIVEN

        // WHEN
        boolean actualReportSafety = reportSafetyService.isReportSafeWhileUsingProblemDampener(report);

        // THEN
        assertEquals(expectedReportSafety, actualReportSafety);
    }

    @Test
    void should_get_number_of_safe_reports_4_from_text_file_inputTest_when_using_problem_dampener() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedSafeReportsNumber = 4;

        // WHEN
        int actualSafeReportsNumber = reportSafetyService.getNumberOfSafeReportsFromFileWhileUsingProblemDampener(fileName);

        // THEN
        assertEquals(expectedSafeReportsNumber, actualSafeReportsNumber);
    }

    private static Stream<Arguments> getReportsWithSafetyStatus() {
        return Stream.of(
                Arguments.of("1 2 3 4 5", true),
                Arguments.of("7 6 5 4 3 2", true),
                Arguments.of("7 4", true),
                Arguments.of("1 5", false),
                Arguments.of("7 2", false),
                Arguments.of("1 2 1", false),
                Arguments.of("3 2 3", false)
        );
    }

    private static Stream<Arguments> getReportsWithSafetyStatusWhenUsingProblemDampener() {
        return Stream.of(
                Arguments.of("1 2 3 4 5", true),
                Arguments.of("7 6 5 4 3 2", true),
                Arguments.of("7 4 3", true),
                Arguments.of("1 5 2", true),
                Arguments.of("7 2 4", true),
                Arguments.of("1 2 1 4", true),
                Arguments.of("3 2 3 1", true),
                Arguments.of("9 3 10 11", true),
                Arguments.of("3 10 2 1", true),
                Arguments.of("3 2 3 3", false),
                Arguments.of("1 2 1 1", false)
        );
    }

}
