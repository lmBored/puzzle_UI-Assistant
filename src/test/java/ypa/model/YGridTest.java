package ypa.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;


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

    @Test
    public void testDuplicateGrid() {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            cells.add(i);
        }
        YGrid grid = new YGrid(cells);
        boolean result = grid.isValuePresent(9);
        assertTrue(result, "duplicate value");
    }

    @Test
    public void testNonDuplicateGrid() {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            cells.add(i);
        }
        cells.add(0);
        YGrid grid = new YGrid(cells);
        boolean result = grid.isValuePresent(9);
        assertFalse(result, "non-duplicate value");
    }

    @Test
    public void testNonFullGrid() {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            cells.add(0);
        }
        YGrid grid = new YGrid(cells);
        boolean result = grid.isFull();
        assertFalse(result, "non-duplicate value");
    }

    @Test
    public void testFullGrid() {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            cells.add(i);
        }
        YGrid grid = new YGrid(cells);
        boolean result = grid.isFull();
        assertTrue(result, "non-duplicate value");
    }

    @Test
    public void testEmptyGrid() {
        ArrayList<Integer> cells = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            cells.add(0);
        }
        YGrid grid = new YGrid(cells);
        boolean result = grid.isEmpty();
        assertTrue(result, "non-duplicate value");
    }
}
