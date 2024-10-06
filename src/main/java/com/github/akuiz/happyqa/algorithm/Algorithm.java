package com.github.akuiz.happyqa.algorithm;

import com.github.akuiz.happyqa.release.Release;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Algorithm {

    /**
     * This method provides best release testing schedule based on a given release list, considering the following
     * release testing must start on the same day of the delivery
     * no 'parallel' testing of releases
     * any taken release must be completed within a sprint
     *
     *
     * @param releaselist given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static List<Release> createOptimalTestingSchedule(List<Release> releaselist, int sprintDuration) {


        List<Release> optimalReleasTestingSchedule = new ArrayList<Release>();

        //remove all 'too late to test' releases
        trimReleaseList(releaselist, sprintDuration);

        // sort the releases by the end testing day in ascending order
        releaselist.sort(Comparator.comparingInt(Release::getEndTestingDay));

        int lastTestingDay = 0;

        for (Release release : releaselist) {
            if(lastTestingDay<release.getDeliveryDay()){
                lastTestingDay = release.getEndTestingDay();
                optimalReleasTestingSchedule.add(release);
            }
        }
        return optimalReleasTestingSchedule;
    }

    /**
     * This method provides best release testing schedule based on a given release list, considering the following
     * release testing can be postponed
     * no 'parallel' testing of releases
     * any taken release must be completed within a sprint
     *
     *
     * @param releaseList given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static List<Release> releaseScheduleAdvanced(List<Release> releaseList, int sprintDuration) {

        int numberOfReleases = 0;

        //remove all 'too late to test' releases
        trimReleaseList(releaseList, sprintDuration);

        while (numberOfReleases != releaseList.size()) {
            // sort the releases by the end testing day in ascending order
            releaseList.sort(Comparator.comparingInt(Release::getEndTestingDay));

            // after sorting, first release is always optimal
            Release optimalRelease = releaseList.get(numberOfReleases);

            numberOfReleases++;

            for (int i = numberOfReleases; i < releaseList.size(); i++) {

                //TO DO EXPLAIN
                if (releaseList.get(i).getStartTestingDay() <= optimalRelease.getEndTestingDay()) {
                    releaseList.get(i).setStartTestingDay(optimalRelease.getEndTestingDay() + 1);
                }
            }
            //remove all 'too late to test' releases
            trimReleaseList(releaseList, sprintDuration);
        }
        return releaseList;
    }

    /**
     * This method removes all 'too late to test' releases from the provided release list based on the sprint duration
     * 
     * @param releaselist given list of releases
     * @param sprintDuration duration of the sprint in days
     */
    public static void trimReleaseList(List<Release> releaselist, int sprintDuration) {
        releaselist.removeIf(release -> release.getEndTestingDay() > sprintDuration);
    }
}
