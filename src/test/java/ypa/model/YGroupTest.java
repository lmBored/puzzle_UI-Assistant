package ypa.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Smoke tests for class {@code YGroup}.
 *
 */
public class YGroupTest {

    /**
     * Tests YCell.
     */
    @Test
    public void testYCell() {
        final YGroup instance;
        instance = new YGroup(1);
        instance.setExpectedSum(4);
        YCell a = new YCell(1);
        List<YCell> l = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            instance.addCell(a);
            l.add(a);
        }
        assertAll(
                () -> assertTrue(instance.equalsExpectedSum(), "equalsExpectedSum"),
                () -> assertTrue(instance.isFull(), "isFull"),
                () -> assertArrayEquals(instance.getCells().toArray(), l.toArray())
        );
    }
}

