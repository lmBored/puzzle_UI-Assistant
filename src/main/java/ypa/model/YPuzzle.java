package ypa.model;

import java.util.Scanner;

/**
 * State of a Sujiko puzzle, without auxiliary solver-related information:
 * the collection of entries, and the states of the cells in the grid
 * (mutable).
 * Acts as a facade for the underlying classes.
 * A Sujiko puzzle has
 * <ul>
 * <li>a mode of operation;
 * <li>a grid of cells YSolutionGrid representing the solution.
 * <li>an array of 4 numbers representing the 4 given hints
 * </ul>
 *
 */
public class YPuzzle {

    /** The possible modes of a puzzle. */
    public enum Mode {
        /** When puzzle can be edited. */
        EDIT,
        /** When puzzle can be solved, but not edited. */
        SOLVE,
        /** When puzzle can only be viewed, but not changed. */
        VIEW
    }

    /** The puzzle's mode. */
    private Mode mode;

    /** The grid of cells. */
    private final YSolutionGrid grid;
    
    /** The array of 4 numbers representing the 4 given hints. */
    private final int[] hints;

    /**
     * Constructs a new puzzle with initial state read from given scanner
     * The actual dimensions are determined from the input.
     *
     * @param scanner  the given scanner
     */
    public YPuzzle(Scanner scanner) {
        this.mode = Mode.VIEW;
        this.grid = new YSolutionGrid();
        this.hints = getHints(scanner);
    }
    
    private static int[] getHints(Scanner sc) {
        int[] hints = new int[4];
        
        for (int i = 0; i < 4; i++) {
            hints[i] = sc.nextInt();
        }
        sc.close();
        
        return hints;
    }

    /**
     * Gets the current mode of this puzzle.
     *
     * @return mode of this
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Sets the mode of this puzzle.
     *
     * @param mode the new mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Checks whether the current state of the puzzle is valid.
     *
     * @return whether the current state of this puzzle is valid
     */
    public boolean isValid() {
        return grid.isValid();
    }

    /**
     * Gets the cells in this puzzle, so as to iterate over them.
     *
     * @return the cells of this puzzle as iterable
     */
    public YSolutionGrid getGrid() {
        return grid;
    }

    
    /**
     * Gets number of cells with a given state.
     *
     * @param state  the given state
     * @return number of cells with state {@code state}
     * @post {@code \result == (\num_of Cell cell : getCells();
     *   cell.getState() == state)}
     
    public int getStateCount(final int state) {
        return grid.getStateCount(state);
    }
    * */

    /**
     * Returns whether puzzle is solved.
     *
     * @return whether puzzle is solved
     */
    public boolean isSolved() {
        return grid.isFull() && grid.isValid();
    }

    /**
     * Clears the non-blocked cells.
     */
    public void clear() {
        grid.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mode: ").append(mode).append("\n");
        sb.append(grid.toString()).append("\n");

        sb.append("Hints:\n");
        sb.append(hints[0]).append(" ").append(hints[1]).append("\n");
        sb.append(hints[2]).append(" ").append(hints[3]).append("\n");

        return sb.toString();
    }

}
