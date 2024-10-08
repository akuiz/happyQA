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
    public static List<Release> releaseScheduleAdvanced(List<Release> releaseList, int sprintDuration) {

        //remove all 'too late to test' releases
        trimReleaseList(releaseList, sprintDuration);

            /*
          Steps of the algorithm:
          1. Sort the release list by startTestingDay (ascending) following by testing time descending.
          2. Go through the sorted list backwards
          3. If an element is not overlapping with an optimal release, make it optimal AND 'move' it as close as possible
          to the optimal release by moving start testing date.
          4. Print list of 'optimal' releases
         */

        // Sort the release list by startTestingDay (ascending) following by testing time descending.
        releaseList.sort((r1, r2) -> {
            // First, compare by startTestingDay (ascending)
            int startTestingComparison = Integer.compare(r1.getStartTestingDay(), r2.getStartTestingDay());
            if (startTestingComparison != 0) {
                return startTestingComparison;
            }
            // If startTestingDay is the same, compare by testing (descending)
            return Integer.compare(r2.getTimeToTest(), r1.getTimeToTest());
        });

        // Initialise an empty release list for optimal release testing schedule
        List<Release> optimalReleaseTestingSchedule = new ArrayList<>();

        /* Placeholder of an 'optimal' release to start with. Delivery&testing day are set after sprint duration to make sure
            last element in the sorted list becomes optimal.
        * */
        Release optimalRelease = new Release(sprintDuration + 1, 1);

        // Go through the sorted release backwards
        for (int i = releaseList.size() - 1; i >= 0; i--) {
            // If a release doesn't overlap with optimal release
            if (releaseList.get(i).getEndTestingDay() < optimalRelease.getStartTestingDay()) {
                // 'Make it' optimal and also 'move' this release next to the optimal by adjusting startTestingday
                optimalRelease = new Release(releaseList.get(i).getDeliveryDay(), optimalRelease.getStartTestingDay() - releaseList.get(i).getTimeToTest(), releaseList.get(i).getTimeToTest());
                optimalRelease.setId(releaseList.get(i).getId());
                // Add optimal release to the final schedule
                optimalReleaseTestingSchedule.add(optimalRelease);
            }
        }

        //Since we were going through the release backwards, it's better to reverse it for better understanding.
        Collections.reverse(optimalReleaseTestingSchedule);
        return optimalReleaseTestingSchedule;
    }
}
