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
        // for each updateList in updateLists:
        // Check if there are any issues
        // if there are issues
        // if no issues:
        // get the middle of the list, and add it to the sum of correct pages to print
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

    private List<PageOrderingRule> getPageOrderingRules(List<String> updateInstructions, int updateInstructionsSeparationIndex) {
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

    private List<List<Integer>> createPagesToPrintList(List<String> updateInstructions, int updateInstructionsSeparationIndex) {
        List<List<Integer>> listOfPagesToPrint = new ArrayList<>();

        List<String> pagesToPrintInstructionsList = updateInstructions.subList(updateInstructionsSeparationIndex + 1, updateInstructions.size());
        for (String pageToPrintInstruction : pagesToPrintInstructionsList) {
            String[] splitPagesToPrintInstruction = pageToPrintInstruction.split(",");
            listOfPagesToPrint.add(Arrays.stream(splitPagesToPrintInstruction).map(Integer::parseInt).toList());
        }

        return listOfPagesToPrint;
    }
    //  for each update page number to print in updateList.split(",")
    //      get page ordering rules that have the update page number as a second page
    //      get all pages after it
    //      for each update page after it
    //          check if the page doesn't appear as a page that has to be printed before
    //              Example incorrect pages to print:
    //                  if we have the list of pages to print 42,1,76, and the current page that we are checking is 42
    //                  if we have 76|42 as an update rule, when we get the list of page ordering rules related to current one, we will get 76
    //                  we will then get the pagesToPrint after this one: 1,76
    //                  cross-referencing the lists will show us that it is incorrect

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
