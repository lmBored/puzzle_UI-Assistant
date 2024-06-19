package ypa.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link YPuzzle}.
 */
public class YPuzzleTest {
    @Test
    public void testInvalidPuzzle() {
        String puzzleString = "20 21 23 4";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        ArrayList<Integer> invalidGridStates = new ArrayList<>(9);
        invalidGridStates.addAll(Collections.nCopies(9, 1));
        YGrid invalidGrid = new YGrid(invalidGridStates);
        puzzle.setGrid(invalidGrid);
        boolean expResult = false;
        boolean result = puzzle.isValid();
        assertEquals(expResult, result, "return value");
    }

    @Test
    public void testValidPuzzle() {
        String puzzleString = "20 21 23 4";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        ArrayList<Integer> validGridStates = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            validGridStates.add(i);
        }
        YGrid validGrid = new YGrid(validGridStates);
        puzzle.setGrid(validGrid);
        boolean expResult = true;
        boolean result = puzzle.isValid();
        assertEquals(expResult, result, "return value");
    }
}
