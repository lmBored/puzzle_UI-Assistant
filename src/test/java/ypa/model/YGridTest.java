package ypa.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Test cases for {@Link YGrid}.
 */
public class YGridTest {
    @Test
    public void testInvalidGrid() {
        ArrayList<Integer> invalidCells = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            invalidCells.add(i);
        }
        invalidCells.add(8);
        invalidCells.add(8);
        YGrid grid = new YGrid(invalidCells);
        boolean result = grid.isValid();
        assertFalse(result, "invalid grid");
    }

    @Test
    public void testValidGrid() {
        ArrayList<Integer> validCells = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            validCells.add(i);
        }
        YGrid grid = new YGrid(validCells);
        boolean result = grid.isValid();
        assertTrue(result, "invalid grid");
    }

}
