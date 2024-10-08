package com.github.akuiz.happyqa;

import com.github.akuiz.happyqa.fileUtils.FileUtils;
import com.github.akuiz.happyqa.algorithm.Algorithm;
import com.github.akuiz.happyqa.release.Release;

import java.io.IOException;
import java.util.List;

public class AdvancedAlgorithm {
    public static void main(String[] args) throws IOException {

        String releasesFileName = System.getProperty("releases.file.name", "releases.txt");
        String outputFileName = System.getProperty("output.file.name", "output.txt");
        String outputFileNameAdvanced = System.getProperty("output.file.name.advanced", "output-advanced.txt");

        // Get sprint duration value. 10 by default
        int sprintDuration = Integer.parseInt(System.getProperty("sprint.duration", "10"));

        List<Release> initialReleaseList = FileUtils.readReleaseDetailsFromFile(releasesFileName);

        List<Release> finalReleaseTestingSchedule = Algorithm.releaseScheduleAdvanced_1(initialReleaseList, sprintDuration);

        // Output releases to test with their original delivery day and end testing day.
        FileUtils.writeReleaseListToFile(finalReleaseTestingSchedule, outputFileName, false);

        // Output release schedule with start testing day and end testing day.
        FileUtils.writeReleaseListToFile(finalReleaseTestingSchedule, outputFileNameAdvanced, true);

    }
}
