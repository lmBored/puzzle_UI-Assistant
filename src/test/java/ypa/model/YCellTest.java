package ypa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke tests for class {@code YCell}.
 *
 */
public class YCellTest {

    /**
     * Tests YCell.
     */
    @Test
    public void testYCell() {
        final YCell instance;
        instance = new YCell(1);
        instance.setLocation(0);
        assertAll(
                () -> assertEquals(1, instance.getState(), "getState"),
                () -> assertFalse(instance.isEmpty(), "isEmpty"),
                () -> assertTrue(instance.isFilled()),
                () -> assertEquals(0, instance.getLocation()),
                () -> assertTrue(instance.isValid())
        );
    }
}
