package com.advent.day4.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CrosswordServiceTest {

    @Autowired
    private CrosswordService crosswordService;

    @Test
    void should_detect_1_XMAS_in_crossword_that_is_written_horizontally() {
        // GIVEN
        List<String> crossword = List.of("XMAS");
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword);

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_vertically() {
        // GIVEN
        String crossword = """
                X
                M
                A
                S
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_diagonally() {
        // GIVEN
        String crossword = """
                X...
                .M..
                ..A.
                ...S
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_diagonally_to_bottom_left() {
        // GIVEN
        String crossword = """
                ...X
                ..M.
                .A..
                S...
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_0_XMAS_in_crossword_when_X_is_at_position_x0_y0() {
        // GIVEN
        String crossword = """
                X...
                ....
                ....
                ....
                """;
        int expectedNumberOfXMASInCrossword = 0;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_to_left() {
        // GIVEN
        String crossword = """
                SAMX
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_in_top_left_direction() {
        // GIVEN
        String crossword = """
                S...
                .A..
                ..M.
                ...X
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_in_up_direction() {
        // GIVEN
        String crossword = """
                ..S.
                ..A.
                ..M.
                ..X.
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_1_XMAS_in_crossword_when_it_is_written_in_up_right_direction() {
        // GIVEN
        String crossword = """
                ...S
                ..A.
                .M..
                X...
                """;
        int expectedNumberOfXMASInCrossword = 1;

        // WHEN
        int actualNumberOfXMASInCrossword = crosswordService.getNumberOfXMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfXMASInCrossword, actualNumberOfXMASInCrossword);
    }

    @Test
    void should_detect_18_XMAS_occurrences_in_text_file_inputTest() {
        // GIVEN
        String fileName = "inputTest.txt";
        int expectedNumberOfXMASInFile = 18;

        // WHEN
        int actualNumberOfXMASInFile = crosswordService.getNumberOfXMASInCrosswordFromFile(fileName);

        // THEN
        assertEquals(expectedNumberOfXMASInFile, actualNumberOfXMASInFile);
    }

    @Test
    void should_detect_1_crossed_MAS_in_crossword_when_first_MAS_is_going_bottom_right_and_second_is_going_bottom_left() {
        // GIVEN
        String crossword = """
                M.M
                .A.
                S.S
                """;
        int expectedNumberOfCrossedMAS = 1;

        // WHEN
        int actualNumberOfCrossedMAS = crosswordService.getNumberOfCrossedMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfCrossedMAS, actualNumberOfCrossedMAS);
    }

    @Test
    void should_detect_1_crossed_MAS_in_crossword_when_first_MAS_is_going_bottom_right_and_second_is_going_top_left() {
        // GIVEN
        String crossword = """
                M.S
                .A.
                M.S
                """;
        int expectedNumberOfCrossedMAS = 1;

        // WHEN
        int actualNumberOfCrossedMAS = crosswordService.getNumberOfCrossedMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfCrossedMAS, actualNumberOfCrossedMAS);
    }

    @Test
    void should_detect_1_crossed_MAS_in_crossword_when_first_MAS_is_going_top_right_and_second_is_going_bottom_left() {
        // GIVEN
        String crossword = """
                S.M
                .A.
                S.M
                """;
        int expectedNumberOfCrossedMAS = 1;

        // WHEN
        int actualNumberOfCrossedMAS = crosswordService.getNumberOfCrossedMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfCrossedMAS, actualNumberOfCrossedMAS);
    }

    @Test
    void should_detect_1_crossed_MAS_in_crossword_when_first_MAS_is_going_top_right_and_second_is_going_top_left() {
        // GIVEN
        String crossword = """
                S.S
                .A.
                M.M
                """;
        int expectedNumberOfCrossedMAS = 1;

        // WHEN
        int actualNumberOfCrossedMAS = crosswordService.getNumberOfCrossedMASInCrossword(crossword.lines().toList());

        // THEN
        assertEquals(expectedNumberOfCrossedMAS, actualNumberOfCrossedMAS);
    }

    @Test
    void should_read_text_file_inputTestWithMAS_and_detect_9_crossed_MAS() {
        // GIVEN
        String fileName = "inputTestWithMAS.txt";
        int expectedNumberOfCrossedMAS = 9;

        // WHEN
        int actualNumberOfCrossedMAS = crosswordService.getNumberOfCrossedMASInCrosswordFile(fileName);
        
        // THEN
        assertEquals(expectedNumberOfCrossedMAS, actualNumberOfCrossedMAS);
    }

}
