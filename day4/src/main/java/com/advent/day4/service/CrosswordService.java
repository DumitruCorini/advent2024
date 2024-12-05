package com.advent.day4.service;

import com.advent.utils.service.FileUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class CrosswordService {
    public int getNumberOfXMASInCrossword(List<String> crosswordLinesList) {
        int numberOfXMAS = 0;

        // Map characters with x,y coordinates
        Map<Point, Character> crosswordLocations = mapCharactersToXYCoordinates(crosswordLinesList);

        // Search all points for X, M, A and S
        Map<Point, Character> xCharacterLocations = getLocationsForCharacter(crosswordLocations, 'X');
        Map<Point, Character> mCharacterLocations = getLocationsForCharacter(crosswordLocations, 'M');
        Map<Point, Character> aCharacterLocations = getLocationsForCharacter(crosswordLocations, 'A');
        Map<Point, Character> sCharacterLocations = getLocationsForCharacter(crosswordLocations, 'S');

        // Check XMAS in all possible directions
        for (Point xCharacter : xCharacterLocations.keySet()) {
            // Check for right
            if (isXMASGoingRight(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check for bottom right
            if (isXMASGoingBottomRight(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check down
            if (isXMASGoingDown(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check bottom left
            if (isXMASGoingBottomLeft(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check left
            if (isXMASGoingLeft(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check top left
            if (isXMASGoingTopLeft(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check top
            if (isXMASGoingTop(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }

            // Check top right
            if (isXMASGoingTopRight(xCharacter, mCharacterLocations, aCharacterLocations, sCharacterLocations)) {
                numberOfXMAS++;
            }
        }

        return numberOfXMAS;
    }

    public int getNumberOfXMASInCrosswordFromFile(String fileName) {
        List<String> fileLines = FileUtils.readFileAndGetLines(fileName);

        return getNumberOfXMASInCrossword(fileLines);
    }

    public int getNumberOfCrossedMASInCrossword(List<String> crosswordLinesList) {
        int numberOfCrossedMAS = 0;

        // Map characters with x,y
        Map<Point, Character> crosswordLocations = mapCharactersToXYCoordinates(crosswordLinesList);

        // Get positions of 'M', 'A' and 'S' characters
        Map<Point, Character> mCharacterLocations = getLocationsForCharacter(crosswordLocations, 'M');
        Map<Point, Character> aCharacterLocations = getLocationsForCharacter(crosswordLocations, 'A');
        Map<Point, Character> sCharacterLocations = getLocationsForCharacter(crosswordLocations, 'S');

        // for each 'M' character, check 
        //  if it is going:
        //      bottom right
        //      or top left
        //  and a second MAS going:
        //      bottom left
        //      or top right
        // Depending on direction of first MAS, detecting second one is different, since we detect using letter positions
        for (Point mCharacterLocation : mCharacterLocations.keySet()) {
            // Check if we have 2 crossed MAS when going bottom right
            if (isFirstMASGoingBottomRight(mCharacterLocation, aCharacterLocations, sCharacterLocations) &&
                hasSecondMASGoingInRightDirectionWhileFirstOneGoesBottomRight(mCharacterLocation, mCharacterLocations, sCharacterLocations)) {
                numberOfCrossedMAS++;
            }

            if (isFirstMASGoingTopLeft(mCharacterLocation, aCharacterLocations, sCharacterLocations) &&
                hasSecondMASGoingInRightDirectionWhileFirstOneGoesTopLeft(mCharacterLocation, mCharacterLocations, sCharacterLocations)) {
                numberOfCrossedMAS++;
            }
        }

        return numberOfCrossedMAS;
    }

    public int getNumberOfCrossedMASInCrosswordFile(String fileName) {
        return getNumberOfCrossedMASInCrossword(FileUtils.readFileAndGetLines(fileName));
    }

    private Map<Point, Character> mapCharactersToXYCoordinates(List<String> crosswordLinesList) {
        Map<Point, Character> charactersMappedToXYPositions = new HashMap<>();

        for (int crosswordLineIdx = 0; crosswordLineIdx < crosswordLinesList.size(); crosswordLineIdx++) {
            String crosswordLine = crosswordLinesList.get(crosswordLineIdx);
            char[] charArray = crosswordLine.toCharArray();
            for (int crosswordLetterIdx = 0; crosswordLetterIdx < charArray.length; crosswordLetterIdx++) {
                char crosswordLetter = charArray[crosswordLetterIdx];
                charactersMappedToXYPositions.put(new Point(crosswordLetterIdx, crosswordLineIdx), crosswordLetter);
            }
        }

        return charactersMappedToXYPositions;
    }

    private Map<Point, Character> getLocationsForCharacter(Map<Point, Character> crosswordLocations, char X) {
        return crosswordLocations.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == X)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isXMASGoingRight(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x + 1, xCharacter.y) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x + 2, xCharacter.y) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x + 3, xCharacter.y);
    }

    private boolean isXMASGoingBottomRight(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x + 1, xCharacter.y + 1) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x + 2, xCharacter.y + 2) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x + 3, xCharacter.y + 3);
    }

    private boolean isXMASGoingDown(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x, xCharacter.y + 1) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x, xCharacter.y + 2) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x, xCharacter.y + 3);
    }

    private boolean isXMASGoingBottomLeft(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x - 1, xCharacter.y + 1) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x - 2, xCharacter.y + 2) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x - 3, xCharacter.y + 3);
    }

    private boolean isXMASGoingLeft(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x - 1, xCharacter.y) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x - 2, xCharacter.y) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x - 3, xCharacter.y);
    }

    private boolean isXMASGoingTopLeft(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x - 1, xCharacter.y - 1) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x - 2, xCharacter.y - 2) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x - 3, xCharacter.y - 3);
    }

    private boolean isXMASGoingTop(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x, xCharacter.y - 1) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x, xCharacter.y - 2) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x, xCharacter.y - 3);
    }

    private boolean isXMASGoingTopRight(Point xCharacter, Map<Point, Character> mCharacterLocations, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(mCharacterLocations, xCharacter.x + 1, xCharacter.y - 1) &&
               doesCharacterExistAtPosition(aCharacterLocations, xCharacter.x + 2, xCharacter.y - 2) &&
               doesCharacterExistAtPosition(sCharacterLocations, xCharacter.x + 3, xCharacter.y - 3);
    }
    /* 
    Example:
    M..
    .A.
    ..S
     */

    private boolean isFirstMASGoingBottomRight(Point mCharacterLocation, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(aCharacterLocations, mCharacterLocation.x + 1, mCharacterLocation.y + 1) &&
               doesCharacterExistAtPosition(sCharacterLocations, mCharacterLocation.x + 2, mCharacterLocation.y + 2);
    }
    /*
    Correct directions: bottom left or top right
    Correct starting position to the right of the 'M' from the first "MAS" or under, depending on direction that it is moving
    We don't have to check the 'A' character because if we have the first "MAS" and we have 'M' and 'S' in second "MAS", then, we will have the 'A' from the first "MAS"
    Possibilities (the 1 and 2 designate the starting positions for 1st and 2nd MAS) :
    1 -> M.M <- 2  |   1 -> M.S
         .A.       |        .A.
         S.S       |   2 -> M.S
     */

    private boolean hasSecondMASGoingInRightDirectionWhileFirstOneGoesBottomRight(Point mCharacterLocation, Map<Point, Character> mCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return (doesCharacterExistAtPosition(mCharacterLocations, mCharacterLocation.x + 2, mCharacterLocation.y) &&
                doesCharacterExistAtPosition(sCharacterLocations, mCharacterLocation.x, mCharacterLocation.y + 2))
               ||
               (doesCharacterExistAtPosition(mCharacterLocations, mCharacterLocation.x, mCharacterLocation.y + 2) &&
                doesCharacterExistAtPosition(sCharacterLocations, mCharacterLocation.x + 2, mCharacterLocation.y));
    }
    /*
    Example:
    S..
    .A.
    ..M
     */

    private boolean isFirstMASGoingTopLeft(Point mCharacterLocation, Map<Point, Character> aCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return doesCharacterExistAtPosition(aCharacterLocations, mCharacterLocation.x - 1, mCharacterLocation.y - 1) &&
               doesCharacterExistAtPosition(sCharacterLocations, mCharacterLocation.x - 2, mCharacterLocation.y - 2);
    }
    /*
    Correct directions: bottom left or top right
    Correct starting position to the right of the 'M' from the first "MAS" or under, depending on direction that it is moving
    We don't have to check the 'A' character because if we have the first "MAS" and we have 'M' and 'S' in second "MAS", then, we will have the 'A' from the first "MAS"
    Possibilities (the 1 and 2 designate the starting positions for 1st and 2nd MAS) :
    S.M <- 2  |       S.S
    .A.       |       .A.
    S.M <- 1  |  2 -> M.M <- 1
     */

    private boolean hasSecondMASGoingInRightDirectionWhileFirstOneGoesTopLeft(Point mCharacterLocation, Map<Point, Character> mCharacterLocations, Map<Point, Character> sCharacterLocations) {
        return (doesCharacterExistAtPosition(mCharacterLocations, mCharacterLocation.x, mCharacterLocation.y - 2) &&
                doesCharacterExistAtPosition(sCharacterLocations, mCharacterLocation.x - 2, mCharacterLocation.y))
               ||
               (doesCharacterExistAtPosition(mCharacterLocations, mCharacterLocation.x - 2, mCharacterLocation.y) &&
                doesCharacterExistAtPosition(sCharacterLocations, mCharacterLocation.x, mCharacterLocation.y - 2));
    }

    private boolean doesCharacterExistAtPosition(Map<Point, Character> characterLocations, int xLocationToCheck, int yLocationToCheck) {
        return !isNull(characterLocations.get(new Point(xLocationToCheck, yLocationToCheck)));
    }
}
