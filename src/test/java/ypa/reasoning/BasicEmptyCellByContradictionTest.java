package ypa.reasoning;

import ypa.command.CompoundCommand;
import ypa.model.YCell;
import ypa.model.YPuzzle;
import ypa.model.YGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

/**
 * This class contains unit tests for the BasicEmptyCellByContradiction class.
 */
public class BasicEmptyCellByContradictionTest {
    private BasicEmptyCellByContradiction reasoner;

    /**
     * Sets up the test environment before each test case.
     */
    @BeforeEach
    void setUp() {
        String puzzleString = "17 18 15 15";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        reasoner = new BasicEmptyCellByContradiction(puzzle);
    }

    /**
     * Tests the checkSolvability method of the BasicEmptyCellByContradiction class.
     */
    @Test
    void testCheckSolvability() {
        String puzzleString = "17 18 15 15";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        YGrid grid = puzzle.getGrid();
        assertTrue(reasoner.checkSolvability(grid));

        String puzzleString1 = "20 23 15 11";
        Scanner scanner1 = new Scanner(puzzleString1);
        YPuzzle puzzle1 = new YPuzzle(scanner1, "Test");
        YGrid unsolvableGrid = puzzle1.getGrid();
        assertTrue(reasoner.checkSolvability(unsolvableGrid));
    }

    /**
     * Tests the applyToCell method of the BasicEmptyCellByContradiction class.
     */
    @Test
    public void testApplyToCell() {
        String puzzleString = "17 18 15 15";
        Scanner scanner = new Scanner(puzzleString);
        YPuzzle puzzle = new YPuzzle(scanner, "Test");
        BasicEmptyCellByContradiction solver = new BasicEmptyCellByContradiction(puzzle);
        YCell cell = new YCell(4);
        CompoundCommand result = solver.apply();
        String expectedCommand = "SetCommand(cell=4, state=1)";
        assertFalse(result.toString().contains(expectedCommand));
    }
}
