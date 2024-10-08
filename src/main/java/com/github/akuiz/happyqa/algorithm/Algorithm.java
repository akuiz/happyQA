package com.github.akuiz.happyqa.algorithm;

import com.github.akuiz.happyqa.release.Release;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Algorithm {

    /**
     * This method provides best release testing schedule based on a given release list, considering the following
     * release testing must start on the same day of the delivery
     * no 'parallel' testing of releases
     * any taken release must be completed within a sprint
     *
     * @param releaseList    given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static List<Release> createOptimalTestingSchedule(List<Release> releaseList, int sprintDuration) {

        List<Release> optimalReleasTestingSchedule = new ArrayList<Release>();

        //remove all 'too late to test' releases
        trimReleaseList(releaseList, sprintDuration);

        /*
          Steps of the algorithm:
          1. Sort the releases by the end testing day in ascending order.
          2. Add first release to the optimal release schedule
          3. skip all parallel releases with optimal release
          4. Add next 'non-overlapping' release to the optimal release schedule
          5. Repeat 3,4 until no releases left
         */

        // Sort the releases by the end testing day in ascending order.
        releaseList.sort(Comparator.comparingInt(Release::getEndTestingDay));

        // Create a placeholder optimal release to add first one from the sorted list as optimal
        Release optimalRelease = new Release(0, 0);//releaseList.get(0);

        // Go through all releases
        for (Release release : releaseList) {
            // Next release that does not overlap with optimal release -> add it to the release schedule and make it optimal.
            if (release.getStartTestingDay() > optimalRelease.getEndTestingDay()) {
                optimalRelease = release;
                optimalReleasTestingSchedule.add(optimalRelease);
            }
        }
        // Optimal release schedule
        return optimalReleasTestingSchedule;
    }

    /**
     * This method provides best release testing schedule based on a given release list, considering the following
     * release testing can be postponed
     * no 'parallel' testing of releases
     * any taken release must be completed within a sprint
     *
     * @param releaseList    given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static List<Release> releaseScheduleAdvanced(List<Release> releaseList, int sprintDuration) {

        //number of optimal releases, also serves as an index for the algorithm
        int numberOfOptimalReleases = 0;

        //remove all 'too late to test' releases
        trimReleaseList(releaseList, sprintDuration);

        List<Release> optimalReleaseTestingSchedule = new ArrayList<Release>();

        /*
          Steps of the algorithm:
          1. Sort the releases by the end testing day in ascending order.
          2. Add first release to the optimal release schedule
          3. Move test starting day of all overlapping releases to next day after last testing day of the optimal release
          4. Remove all 'too late to test' releases
          5. Repeat from 1 until number of optimal release is not equal size of the release list after trimming
         */

        while (numberOfOptimalReleases != releaseList.size()) {
            // sort the releases by the end testing day in ascending order
            releaseList.sort(Comparator.comparingInt(Release::getEndTestingDay));

            // after sorting, first release is always optimal
            Release optimalRelease = releaseList.get(numberOfOptimalReleases);
            optimalReleaseTestingSchedule.add(optimalRelease);

            //increase number of optimal releases
            numberOfOptimalReleases++;

            // go through all releases starting from the next after optimal
            for (int i = numberOfOptimalReleases; i < releaseList.size(); i++) {
                //Move test starting day of all overlapping releases to next day after last testing day of the optimal release
                if (releaseList.get(i).getStartTestingDay() <= optimalRelease.getEndTestingDay()) {
                    releaseList.get(i).setStartTestingDay(optimalRelease.getEndTestingDay() + 1);
                }
            }
            //remove all 'too late to test' releases
            trimReleaseList(releaseList, sprintDuration);
        }
        return optimalReleaseTestingSchedule;
    }

    /**
     * This method removes all 'too late to test' releases from the provided release list based on the sprint duration
     *
     * @param releaselist    given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static void trimReleaseList(List<Release> releaselist, int sprintDuration) {
        releaselist.removeIf(release -> release.getEndTestingDay() > sprintDuration);
    }

    /**
     * This method provides best release testing schedule based on a given release list, considering the following
     * release testing can be postponed
     * no 'parallel' testing of releases
     * any taken release must be completed within a sprint
     *
     * @param releaseList    given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static List<Release> releaseScheduleAdvanced_1(List<Release> releaseList, int sprintDuration) {

        //remove all 'too late to test' releases
        trimReleaseList(releaseList, sprintDuration);

        releaseList.sort((r1, r2) -> {
            // First, compare by startTestingDay (ascending)
            int startTestingComparison = Integer.compare(r1.getStartTestingDay(), r2.getStartTestingDay());
            if (startTestingComparison != 0) {
                return startTestingComparison;
            }
            // If startTestingDay is the same, compare by size (descending)
            return Integer.compare(r2.getTimeToTest(), r1.getTimeToTest());
        });

        List<Release> optimalReleaseTestingSchedule = new ArrayList<Release>();
        Release optimalRelease = new Release(sprintDuration + 1, 1);

        /*
          Steps of the algorithm:
          1. TO DO
         */

        for (int i = releaseList.size() - 1; i >= 0; i--) {
            if (releaseList.get(i).getEndTestingDay() < optimalRelease.getStartTestingDay()) {
                releaseList.get(i).setEndTestingDay(optimalRelease.getStartTestingDay() - 1);
                optimalRelease = releaseList.get(i);
                optimalReleaseTestingSchedule.add(optimalRelease);
            }
        }
        Collections.reverse(optimalReleaseTestingSchedule);
        return optimalReleaseTestingSchedule;
    }
}
