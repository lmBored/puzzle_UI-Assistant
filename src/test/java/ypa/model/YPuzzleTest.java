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

    @Test
    public void testGetIndexCircle() {
        String puzzleString = "20 21 23 4";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        int expResult = 1;
        int result = puzzle.getIndexSelectedCircle(21);
        assertEquals(expResult, result, "return value");
    }

    @Test
    public void testGetIndexCircleNotFound() {
        String puzzleString = "20 21 23 4";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        assertThrows(IllegalArgumentException.class, () -> puzzle.getIndexSelectedCircle(22));
    }

    @Test
    public void testCheckSolvedPuzzle() {
        String puzzleString = "21 17 28 27";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        ArrayList<Integer> solvedGrid = new ArrayList<>();
        solvedGrid.add(3);
        solvedGrid.add(1);
        solvedGrid.add(2);
        solvedGrid.add(8);
        solvedGrid.add(9);
        solvedGrid.add(5);
        solvedGrid.add(4);
        solvedGrid.add(7);
        solvedGrid.add(6);
        YGrid validGrid = new YGrid(solvedGrid);
        puzzle.setGrid(validGrid);
        boolean expResult = true;
        boolean result = puzzle.isSolved();
        assertEquals(expResult, result, "solved puzzle");
    }

    @Test
    public void testCheckUnsolvedPuzzle() {
        String puzzleString = "21 17 28 27";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        ArrayList<Integer> unsolvedGrid = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            unsolvedGrid.add(i);
        }
        YGrid invalidGrid = new YGrid(unsolvedGrid);
        puzzle.setGrid(invalidGrid);
        boolean expResult = false;
        boolean result = puzzle.isSolved();
        assertEquals(expResult, result, "unsolved puzzle");
    }
}
