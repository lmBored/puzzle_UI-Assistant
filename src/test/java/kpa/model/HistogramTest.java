package kpa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@code Histogram}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HistogramTest {

    /** Subject Under Test. */
    private Histogram instance;

    @BeforeEach
    public void setUp() {
        instance = new Histogram();
    }

    /**
     * Checks SUT state to be equal to given array.
     */
    private void checkSUT(final int[] expected) {
        int result;
        for (int state = KCell.BLOCKED; state <= 1; ++state) {
            result = instance.get(state);
            assertEquals(expected[state - KCell.BLOCKED], result, "Count for state " + state);
        }
    }

    /**
     * Tests constructor, of class Histogram.
     */
    @Test
    public void testConstructor() {
        System.out.println("Histogram()");
        checkSUT(new int[3]);
    }

    /**
     * Tests adjust method, of class Histogram.
     */
    @Test
    public void testAdjust() {
        System.out.println("adjust");
        int[] expected = new int[4];
        int delta = 1;
        for (int state = KCell.BLOCKED; state <= 1; ++state) {
            instance.adjust(state, delta);
            expected[state - KCell.BLOCKED] += delta;
            ++delta;
            checkSUT(expected);
        }
    }

}
