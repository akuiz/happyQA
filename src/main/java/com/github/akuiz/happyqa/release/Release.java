package com.github.akuiz.happyqa.release;


import java.util.Objects;

/**
 * This class represents a Release from Happy QA point of view.
 * It provides multiple attributes for better release testing scheduling and management, like
 * Release Delivery day, Time to Test, Start Testing day, etc.
 * This class also includes methods to get and update this information for a particular release.
 * Also included basic and detailed way for printing the release information.
 */

public class Release {

    /**
     * #day of the sprint when the release is delivered for testing
     */
    private int deliveryDay;

    /**
     * How many days it takes to test the release
     */
    private final int timeToTest;

    /**
     * #day of the sprint when the testing of the release is supposed to start
     */
    private int startTestingDay;

    /**
     * #day of the sprint when the testing of the release is finished
     */
    private int endTestingDay;

    /**
     * Set id to the release
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Unique ID of the release (this field initialised at the time of reading release inputs from the input file)
     */
    private String id;

    /**
     * Creates Release object based on the following parameters:
     *
     * @param deliveryDay #day of the sprint when the release is delivered for testing
     * @param timeToTest  how many days it takes to test the release
     */
    public Release(int deliveryDay, int timeToTest) {

        this.deliveryDay = deliveryDay;
        this.timeToTest = timeToTest;

        // By default, testing of the release is supposed to start on the same day of delivery
        this.startTestingDay = deliveryDay;

        /*
        #day when the release will be completely tested.
        If endTestingDay > sprint.duration (based on the config)
        then the release can't be tested within the same sprint.
         */
        this.endTestingDay = deliveryDay + timeToTest - 1;
    }

    /**
     * Creates Release object based on the following parameters:
     *
     * @param deliveryDay #day of the sprint when the release is delivered for testing
     * @param startTestingDay #day of the sprint when the release is supposed to start the testing
     * @param timeToTest  how many days it takes to test the release
     */
    public Release(int deliveryDay, int startTestingDay, int timeToTest) {

        this.deliveryDay = deliveryDay;
        this.timeToTest = timeToTest;

        // By default, testing of the release is supposed to start on the same day of delivery
        this.startTestingDay = startTestingDay;

        /*
        #day when the release will be completely tested.
        If endTestingDay > sprint.duration (based on the config)
        then the release can't be tested within the same sprint.
         */
        this.endTestingDay = startTestingDay + timeToTest - 1;
    }

    public int getStartTestingDay() {
        return startTestingDay;
    }

    /**
     * Sets start testing day for a specific day in the sprint.
     * end testing day gets adjusted accordingly based on required time to test
     *
     * @param startTestingDay start testing day in the sprint for this release
     */
    public void setStartTestingDay(int startTestingDay) {
        this.startTestingDay = startTestingDay;
        this.endTestingDay = startTestingDay + timeToTest - 1;
    }

    public int getDeliveryDay() {
        return deliveryDay;
    }

    public int getEndTestingDay() {
        return endTestingDay;
    }

    /**
     * Provides basic information about the release.
     * Basic information includes only delivery day and end testing day.
     *
     * @return basic details about the release as String
     */
    public String toString() {
        return startTestingDay + " " + endTestingDay;
    }

    /**
     * Provides detailed or basic information about the release.
     * Basic information includes only delivery day and end testing day.
     * Detailed information includes all basic information, start testing day, and ID of the release.
     *
     * @param detailed parameter to display basic or detailed info about the release
     * @return details about the release as String
     */
    public String toString(boolean detailed) {
        if (detailed) {
            return "Release " + id + " {" +
                    "startTestingDay=" + startTestingDay +
                    ", endTestingDay=" + endTestingDay +
                    ", deliveryDay=" + deliveryDay +
                    ", timeToTest=" + timeToTest +
                    '}';
        } else {
            return toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Release release = (Release) o;
        return deliveryDay == release.deliveryDay &&
                timeToTest == release.timeToTest &&
                startTestingDay == release.startTestingDay &&
                endTestingDay == release.endTestingDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryDay, timeToTest, startTestingDay, endTestingDay);
    }
}