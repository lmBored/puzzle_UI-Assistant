package ypa.solvers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import ypa.model.YPuzzle;
import ypa.reasoning.Reasoner;

/**
 * Test cases for {@link YBacktrackSolver}.
 */
public class YBacktrackSolverTest {
    @Test
    public void testSolveWithoutReasoner() {
        String puzzleString = "1 2 3 4";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        YBacktrackSolver instance = new YBacktrackSolver(puzzle, null);
        boolean expResult = false;
        boolean result = instance.solve();
        assertEquals(expResult, result, "return value");
    }

    @Test
    public void testSolvablePuzzle() {
        String puzzleString = "20 20 23 21";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        Reasoner reasoner = new Reasoner(puzzle);
        YBacktrackSolver instance = new YBacktrackSolver(puzzle, reasoner);
        boolean expResult = true;
        boolean result = instance.solve();
        assertEquals(expResult, result, "return value");
    }

    @Test
    public void testUnsolvablePuzzle() {
        String puzzleString = "1 2 3 4";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        Reasoner reasoner = new Reasoner(puzzle);
        YBacktrackSolver instance = new YBacktrackSolver(puzzle, reasoner);
        boolean expResult = false;
        boolean result = instance.solve();
        assertEquals(expResult, result, "return value");
    }
}