package com.github.akuiz.happyqa.fileUtils;

import com.github.akuiz.happyqa.release.Release;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is a utility class that provides functionality to read and write
 * release details from the files in a certain format.
 */

public class FileUtils {

    /**
     * Reads release list details from an input file.
     * Each release on a new line with delivery day, time to test separated by space
     *
     * @param filePath the path to the input file.
     * @return a list of releases
     * @throws IOException if an I/O error occurs.
     */
    public static List<Release> readReleaseDetailsFromFile(String filePath) throws IOException {

        List<Release> releases = new ArrayList<>();

        // releaseCounter variable is used to properly assign ID to a release
        int releaseCounter = 0;

        // Go through each line of the input file and parse delivery day & time to test values
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Split each line by space
                String[] parts = line.split(" ");

                // Delivery day
                int releaseDeliveryDay = Integer.parseInt(parts[0]);

                // Time to test
                int timeToTest = Integer.parseInt(parts[1]);

                // Initialise a new release
                Release release = new Release(releaseDeliveryDay, timeToTest);

                // Set id to the release based on the release counter
                release.setId(generateId(releaseCounter));

                // Add the release to the list
                releases.add(release);

                // Increase releaseCounter to properly set ID for the next release
                releaseCounter++;
            }
        }
        return releases;
    }

    /**
     * Writes release list size followed by info about each release to an output file.
     *
     * @param releaseList is the release list
     * @param filename is a path to output file
     * @param detailed to print all details about releases. If set to false, only Delivery Day of the release and End Day are printed.
     */
    public static void writeReleaseListToFile(List<Release> releaseList, String filename, boolean detailed) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.valueOf(releaseList.size()));
            writer.newLine();
            for (Release release : releaseList) {
                writer.write(release.toString(detailed));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates alphabetic IDs (A, B, ..., Z, AA, AB, ..., ZZ, and so on) for releases
     *
     * @param index is a counter to follow an order of the releases
     */
    public static String generateId(int index) {
        StringBuilder label = new StringBuilder();
        while (index >= 0) {
            label.insert(0, (char) ('A' + index % 26));
            index = index / 26 - 1;
        }
        return label.toString();
    }
}