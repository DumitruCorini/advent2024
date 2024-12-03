package com.advent.day2.service;

import com.advent.utils.service.FileUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportSafetyService {

    public boolean isReportSafe(String report) {
        List<String> splitReport = new ArrayList<>(List.of(report.split(" ")));
        return getReportSafety(splitReport);
    }

    public int getNumberOfSafeReportsFromFile(String fileName) {
        int numberOfSafeReports = 0;
        List<String> reports = FileUtils.readFileAndGetLines(fileName);

        for (String report : reports) {
            if (isReportSafe(report)) {
                numberOfSafeReports++;
            }
        }

        return numberOfSafeReports;
    }


    public boolean isReportSafeWhileUsingProblemDampener(String report) {
        List<String> splitReport = new ArrayList<>(List.of(report.split(" ")));
        boolean isAscending = Integer.parseInt(splitReport.get(0)) < Integer.parseInt(splitReport.get(1));
        boolean problemDampenerTriggered = false;

        for (int reportIdx = 0; reportIdx < splitReport.size() - 1; reportIdx++) {
            int firstReportDataToCheck = Integer.parseInt(splitReport.get(reportIdx));
            int secondReportDataToCheck = Integer.parseInt(splitReport.get(reportIdx + 1));
            int reportDataDifference = Math.abs(secondReportDataToCheck - firstReportDataToCheck);

            if ((reportDataDifference > 3) || (reportDataDifference == 0) ||
                (isAscending && firstReportDataToCheck > secondReportDataToCheck) ||
                (!isAscending && firstReportDataToCheck < secondReportDataToCheck)) {
                problemDampenerTriggered = true;
                break;
            }
        }

        if (problemDampenerTriggered) {
            // Find if we can remove a problem at any index to make the report safe
            for (int reportItemIdx = 0; reportItemIdx < splitReport.size(); reportItemIdx++) {
                List<String> reportToCheck = new ArrayList<>(splitReport);
                reportToCheck.remove(reportItemIdx);

                if (getReportSafety(reportToCheck)) {
                    return true;
                }
            }
            return false;
        }

        return true;
    }

    public int getNumberOfSafeReportsFromFileWhileUsingProblemDampener(String fileName) {
        int numberOfSafeReports = 0;
        List<String> reports = FileUtils.readFileAndGetLines(fileName);

        for (String report : reports) {
            if (isReportSafeWhileUsingProblemDampener(report)) {
                numberOfSafeReports++;
            }
        }

        return numberOfSafeReports;
    }

    private boolean getReportSafety(List<String> splitReport) {
        boolean isAscending = Integer.parseInt(splitReport.get(0)) < Integer.parseInt(splitReport.get(1));

        for (int reportIdx = 0; reportIdx < splitReport.size() - 1; reportIdx++) {
            int firstReportDataToCheck = Integer.parseInt(splitReport.get(reportIdx));
            int secondReportDataToCheck = Integer.parseInt(splitReport.get(reportIdx + 1));
            int reportDataDifference = Math.abs(secondReportDataToCheck - firstReportDataToCheck);

            if ((reportDataDifference > 3) || (reportDataDifference == 0) ||
                (isAscending && firstReportDataToCheck > secondReportDataToCheck) ||
                (!isAscending && firstReportDataToCheck < secondReportDataToCheck)) {
                return false;
            }
        }
        return true;
    }
}
