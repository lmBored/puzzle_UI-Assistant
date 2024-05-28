package kpa.reasoning;

import kpa.command.CompoundCommand;
import kpa.model.KPuzzle;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link Reasoner}.
 *
 * @author wstomv
 */
public class ReasonerTest {

    public static final String PUZZLE = "a 1 - 3 2\nb 1 - 7 2\na 1 | 4 2\na 2 | 6 2\n";

    /** Puzzle for testing. */
    private final KPuzzle puzzle;

    public ReasonerTest() {
        puzzle = new KPuzzle(new Scanner(PUZZLE), "Test");
    }

    /**
     * Test of apply method, of class Reasoner.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        Reasoner instance = new Reasoner(puzzle);
        CompoundCommand result = instance.apply();
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed")
        );
    }

}
