package com.advent.day5.service;

import com.advent.day5.model.PageOrderingRule;
import com.advent.utils.service.FileUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PrintingService {

    public int getSumOfCorrectUpdates(List<String> updateInstructions) {
        // Setup
        // Find where pageOrdering rules end and pages to print start
        int updateInstructionsSeparationIndex = updateInstructions.indexOf("");

        // Get page ordering rules from update instructions
        List<PageOrderingRule> pageOrderingRules = getPageOrderingRules(updateInstructions, updateInstructionsSeparationIndex);

        // Get update lists from update instructions
        List<List<Integer>> listOfPagesToPrint = createPagesToPrintList(updateInstructions, updateInstructionsSeparationIndex);

        // Execution
        // Check update pages to print
        // for each pagesToPrint in listOfPagesToPrint:
        // Check if there are any issues
        // if no issues:
        //      get the middle of the list, and add it to the sum of correct pages to print
        int correctPagesToPrintSum = 0;
        for (List<Integer> pagesToPrint : listOfPagesToPrint) {
            if (!issuesPresentInCurrentPagePrintInstructions(pagesToPrint, pageOrderingRules)) {
                Integer middlePageToPrint = pagesToPrint.get(pagesToPrint.size() / 2);
                correctPagesToPrintSum += middlePageToPrint;
            }
        }

        return correctPagesToPrintSum;
    }

    public int getSumOfCorrectUpdatesFromFile(String fileName) {
        return getSumOfCorrectUpdates(FileUtils.readFileAndGetLines(fileName));
    }

    public int getSumOfIncorrectUpdates(List<String> updateInstructions) {
        // Setup
        // Find where pageOrdering rules end and pages to print start
        int updateInstructionsSeparationIndex = updateInstructions.indexOf("");

        // Get page ordering rules from update instructions
        List<PageOrderingRule> pageOrderingRules = getPageOrderingRules(updateInstructions, updateInstructionsSeparationIndex);

        // Get update lists from update instructions
        List<List<Integer>> listOfPagesToPrint = createPagesToPrintList(updateInstructions, updateInstructionsSeparationIndex);

        // Execution
        // Check update pages to print
        // for each pagesToPrint in listOfPagesToPrint:
        // Check if there are any issues
        // if there are issues:
        //      reorder the list to follow the correct order
        //      get the middle of the list, and add it to the sum of incorrect pages to print
        int incorrectPagesToPrintSum = 0;
        for(List<Integer> pagesToPrint: listOfPagesToPrint) {
            if (issuesPresentInCurrentPagePrintInstructions(pagesToPrint, pageOrderingRules)) {
                List<Integer> correctlyReorderedPages = reorderPagesToPrintToFollowPageOrderingRules(pagesToPrint, pageOrderingRules);
                Integer middlePageToPrint = correctlyReorderedPages.get(correctlyReorderedPages.size() / 2);
                incorrectPagesToPrintSum += middlePageToPrint;
            }
        }

        return incorrectPagesToPrintSum;
    }

    public int getSumOfIncorrectUpdatesFromFile(String fileName) {
        return getSumOfIncorrectUpdates(FileUtils.readFileAndGetLines(fileName));
    }

    // To reorder, go through the list of pages to print
    //      get all second page ordering rules that have a page to print as the first page
    //      get the first index of second page ordering rules in correctlyOrderedPages
    //      if there is no second page ordering rule already present (index = -1):
    //          add to end of correctlyOrderedPages, since no pages depend on the current page
    //      else:
    //          insert at the first index, since all the second pages will depend on current page
    //
    // Example:
    //      Ordering rule: 9|10
    //      Pages to print: 5,10,9
    //      Going through the list of items:
    //          "5": there are no ordering rules that contain it as the first page, add it to end of list
    //          "10": there are no ordering rules that contain it as the first page, add it to end of list
    //          "9": there is ordering rule "9|10", there is "10" at index "1", insert "9" at index "1", so that resulting list if "1,9,10"
    protected List<Integer> reorderPagesToPrintToFollowPageOrderingRules(List<Integer> pagesToPrint, List<PageOrderingRule> pageOrderingRules) {
        List<Integer> correctlyOrderedPages = new ArrayList<>();

        for (Integer pageToPrint : pagesToPrint) {
            int firstAlreadyOrderedPageIndex = getFirstAlreadyOrderedPageIndex(pageOrderingRules, pageToPrint, correctlyOrderedPages);

            if (firstAlreadyOrderedPageIndex == -1) {
                correctlyOrderedPages.add(pageToPrint);
            } else {
                correctlyOrderedPages.add(firstAlreadyOrderedPageIndex, pageToPrint);
            }
        }

        return correctlyOrderedPages;
    }

    private int getFirstAlreadyOrderedPageIndex(List<PageOrderingRule> pageOrderingRules, Integer pageToPrint, List<Integer> correctlyOrderedPages) {
        if (correctlyOrderedPages.isEmpty()) {
            return -1;
        }

        List<Integer> pageOrderingRulesRelatedToCurrentPage = pageOrderingRules.stream()
                .filter(pageOrderingRule -> pageOrderingRule.getFirstPage().equals(pageToPrint))
                .map(PageOrderingRule::getSecondPage)
                .toList();

        int firstAlreadyOrderedPageIndex = -1;
        for (Integer secondPageToCheck: pageOrderingRulesRelatedToCurrentPage) {
            int alreadyOrderedPageIndex = correctlyOrderedPages.indexOf(secondPageToCheck);
            if (alreadyOrderedPageIndex != -1) {
                if (firstAlreadyOrderedPageIndex == -1) {
                    firstAlreadyOrderedPageIndex = alreadyOrderedPageIndex;
                } else if (alreadyOrderedPageIndex < firstAlreadyOrderedPageIndex) {
                    firstAlreadyOrderedPageIndex = alreadyOrderedPageIndex;
                }
            }
        }
        return firstAlreadyOrderedPageIndex;
    }

    List<PageOrderingRule> getPageOrderingRules(List<String> updateInstructions, int updateInstructionsSeparationIndex) {
        List<PageOrderingRule> pageOrderingRules = new ArrayList<>();
        List<String> pageOrderingRulesInstructionsList = updateInstructions.subList(0, updateInstructionsSeparationIndex);

        for (String updateInstruction : pageOrderingRulesInstructionsList) {
            String[] splitUpdateInstruction = updateInstruction.split("\\|");
            pageOrderingRules.add(PageOrderingRule.builder()
                    .firstPage(Integer.parseInt(splitUpdateInstruction[0]))
                    .secondPage(Integer.parseInt(splitUpdateInstruction[1]))
                    .build());
        }

        return pageOrderingRules;
    }

    List<List<Integer>> createPagesToPrintList(List<String> updateInstructions, int updateInstructionsSeparationIndex) {
        List<List<Integer>> listOfPagesToPrint = new ArrayList<>();

        List<String> pagesToPrintInstructionsList = updateInstructions.subList(updateInstructionsSeparationIndex + 1, updateInstructions.size());
        for (String pageToPrintInstruction : pagesToPrintInstructionsList) {
            String[] splitPagesToPrintInstruction = pageToPrintInstruction.split(",");
            listOfPagesToPrint.add(Arrays.stream(splitPagesToPrintInstruction).map(Integer::parseInt).toList());
        }

        return listOfPagesToPrint;
    }

    //  for each update page number to print in pagesToPrint.split(",")
    //      get page ordering rules that have the update page number as a second page
    //      get all pages after it
    //      for each update page after it
    //          check if the page doesn't appear as a page that has to be printed before
    //              Example incorrect pages to print:
    //                  if we have the list of pages to print 42,1,76, and the current page that we are checking is 42
    //                  if we have 76|42 as an update rule, when we get the list of page ordering rules related to current one, we will get 76
    //                  we will then get the pagesToPrint after this one: 1,76
    //                  cross-referencing the lists will show us that it is incorrect, because 42 should come after 76
    private boolean issuesPresentInCurrentPagePrintInstructions(List<Integer> pagesToPrint, List<PageOrderingRule> pageOrderingRules) {
        for (int pageToPrintIdx = 0; pageToPrintIdx < pagesToPrint.size() - 1; pageToPrintIdx++) {
            Integer pageToPrint = pagesToPrint.get(pageToPrintIdx);
            List<Integer> pageOrderingRulesRelatedToCurrentPage = pageOrderingRules.stream()
                    .filter(pageOrderingRule -> pageOrderingRule.getSecondPage().equals(pageToPrint))
                    .map(PageOrderingRule::getFirstPage)
                    .toList();
            List<Integer> pagesToPrintAfterThisOne = pagesToPrint.subList(pageToPrintIdx + 1, pagesToPrint.size());
            // Find if any items in rules related to current page are in pagesToPrint
            Optional<Integer> foundIssue = pageOrderingRulesRelatedToCurrentPage.stream().filter(pagesToPrintAfterThisOne::contains).findFirst();
            if (foundIssue.isPresent()) {
                return true;
            }
        }
        return false;
    }
}
