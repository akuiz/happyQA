import com.github.akuiz.happyqa.algorithm.Algorithm;
import com.github.akuiz.happyqa.release.Release;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvancedAlgorithmTest {
    @Test
    public void testAdvancedAlgorithmDescriptionExample() {

        List<Release> inputReleases = new ArrayList<>(Arrays.asList(
                new Release(1, 1),
                new Release(2, 1),
                new Release(3, 1),
                new Release(9, 1),
                new Release(10, 4),
                new Release(10, 2),
                new Release(9, 5),
                new Release(10, 3),
                new Release(4, 5)
        ));

        List<Release> expectedOutput = new ArrayList<>(Arrays.asList(
                new Release(1, 2, 1),
                new Release(2, 3, 1),
                new Release(3, 4, 1),
                new Release(4, 5, 5),
                new Release(9, 10, 1)
        ));

        List<Release> actualOutput = Algorithm.releaseScheduleAdvanced(inputReleases, 10);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testAdvancedAlgorithmNoReleasesCanBeTested() {

        List<Release> inputReleases = new ArrayList<>(Arrays.asList(
                new Release(10, 4),
                new Release(10, 2),
                new Release(9, 5),
                new Release(10, 3)
        ));

        List<Release> actualOutput = Algorithm.releaseScheduleAdvanced(inputReleases, 10);

        assertEquals(0, actualOutput.size());
    }

    @Test
    public void testAdvancedAlgorithmOneReleaseToTest() {

        List<Release> inputReleases = new ArrayList<>(Arrays.asList(
                new Release(10, 2),
                new Release(5, 1),
                new Release(9, 5),
                new Release(10, 3)
        ));

        List<Release> expectedOutput = new ArrayList<>(Arrays.asList(
                new Release(5, 10, 1)
        ));

        List<Release> actualOutput = Algorithm.releaseScheduleAdvanced(inputReleases, 10);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testAdvancedAlgorithmManyReleases() {

        List<Release> inputReleases = new ArrayList<>(Arrays.asList(
                new Release(1, 6),
                new Release(2, 3),
                new Release(4, 2),
                new Release(4, 5),
                new Release(6, 4),
                new Release(7, 4),
                new Release(9, 2),
                new Release(5, 3)
        ));

        List<Release> expectedOutput = new ArrayList<>(Arrays.asList(
                new Release(4, 4, 2),
                new Release(5, 6, 3),
                new Release(9, 9, 2)
        ));

        List<Release> actualOutput = Algorithm.releaseScheduleAdvanced(inputReleases, 10);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testAdvancedAlgorithmBackToBack() {

        List<Release> inputReleases = new ArrayList<>(Arrays.asList(
                new Release(1, 1),
                new Release(2, 1),
                new Release(3, 1),
                new Release(4, 1),
                new Release(5, 1),
                new Release(6, 1),
                new Release(7, 1),
                new Release(8, 1),
                new Release(9, 1),
                new Release(10, 1)
        ));

        List<Release> expectedOutput = new ArrayList<>(Arrays.asList(
                new Release(1, 1),
                new Release(2, 1),
                new Release(3, 1),
                new Release(4, 1),
                new Release(5, 1),
                new Release(6, 1),
                new Release(7, 1),
                new Release(8, 1),
                new Release(9, 1),
                new Release(10, 1)
        ));

        List<Release> actualOutput = Algorithm.releaseScheduleAdvanced(inputReleases, 10);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testAdvancedAlgorithmOneOverlap() {

        List<Release> inputReleases = new ArrayList<>(Arrays.asList(
                new Release(1, 2),
                new Release(2, 1),
                new Release(3, 1),
                new Release(5, 2),
                new Release(4, 5)
        ));

        List<Release> expectedOutput = new ArrayList<>(Arrays.asList(
                new Release(2, 2, 1),
                new Release(3, 3, 1),
                new Release(4, 4, 5),
                new Release(5, 9, 2)
        ));

        List<Release> actualOutput = Algorithm.releaseScheduleAdvanced(inputReleases, 10);

        assertEquals(expectedOutput, actualOutput);
    }
}
