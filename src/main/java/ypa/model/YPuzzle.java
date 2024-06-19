package ypa.model;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import ypa.reasoning.Reasoner;
import ypa.solvers.YAbstractSolver;
import ypa.solvers.YBacktrackSolver;

/**
 * State of a Sujiko puzzle, without auxiliary solver-related information:
 * the collection of entries, and the states of the cells in the grid
 * (mutable).
 * Acts as a facade for the underlying classes.
 * A Sujiko puzzle has
 * <ul>
 * <li>a name;
 * <li>a mode of operation;
 * <li>a grid of cells YGrid representing the solution;
 * <li>an array of 4 numbers representing the 4 given hints / circles;
 * </ul>
 *
 */
public class YPuzzle {

    /** The puzzle's (file) name. */
    private String name;

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

    private YGrid grid;

    /** The array of 4 numbers representing the 4 given hints. */
    private final int[] circles;

    /** Error message to display. */
    public static String errormsg;

    /**
     * Constructs a new puzzle with initial state read from given scanner
     * and with a given name.
     * The actual dimensions are determined from the input.
     *
     * @param scanner the given scanner
     * @param name    the given name
     */
    public YPuzzle(final Scanner scanner, final String name) {
        this.name = name;
        this.mode = Mode.VIEW;
        this.grid = new YGrid();
        this.circles = createCircles(scanner);
        List<YGroup> list = grid.getGroups();
        for (int i = 0; i < 4; i++) {
            list.get(i).setExpectedSum(circles[i]);
        }
        while (scanner.hasNext()) {
            String rowChar = scanner.next();
            int row = rowChar.charAt(0) - 'a';
            int col = scanner.nextInt();
            int value = scanner.nextInt();

            int position = row * 3 + col; // Convert row and column to position
            this.grid.setCell(position, value);
        }
        scanner.close();
    }

    private static int[] createCircles(Scanner sc) {
        int[] circles = new int[4];

        for (int i = 0; i < 4; i++) {
            circles[i] = sc.nextInt();
        }
        // sc.close();

        return circles;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the name of this puzzle. Only allowed in edit mode.
     *
     * @param name the new name
     * @throws IllegalStateException if not in edit mode
     * @pre puzzle is in edit mode
     */
    public void setName(String name) {
        if (mode != Mode.EDIT) {
            throw new IllegalStateException(this.getClass().getSimpleName()
                    + ".setName(): not in EDIT mode");
        }
        this.name = name;
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
     * Checks the solvability of the puzzle.
     *
     * @return true if the puzzle is solvable, false otherwise.
     */
    private boolean checkPuzzleSolvability(YPuzzle puzzle) {
        Reasoner reasoner = null;
        YAbstractSolver solver = new YBacktrackSolver(puzzle, reasoner);

        if (solver.isSolvable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the mode of this puzzle with optional param puzzle.
     *
     * @param mode the new mode
     */
    public void setMode(Mode mode) {
        setMode(mode, this);
    }

    /**
     * Sets the mode of this puzzle.
     *
     * @param mode   the new mode
     * @param puzzle the puzzle to check for solvability
     */
    public void setMode(Mode mode, YPuzzle puzzle) {
        if (mode == Mode.SOLVE && !checkPuzzleSolvability(puzzle)) {
            errormsg = "The puzzle is not solvable. Cannot switch to SOLVE mode.";
            setMode(Mode.EDIT);
            return;
        }
        this.mode = mode;
    }

    /**
     * Checks whether the current state of the puzzle is valid.
     *
     * @return whether the current state of this puzzle is valid
     */
    public boolean isValid() {
        // return grid.isValid();

        // Check if the grid is valid
        if (!grid.isValid()) {
            return false;
        }

        // Check for violated cells
        List<YCell> violatedCells = getViolatedCells();
        if (!violatedCells.isEmpty()) {
            return false;
        }

        // If there are no violated cells and the grid is valid, the puzzle is valid
        return true;
    }

    /**
     * Gets the cells in this puzzle, so as to iterate over them.
     *
     * @return the cells of this puzzle as iterable
     */
    public YGrid getGrid() {
        return grid;
    }

    /**
     * Updates the puzzle grid.
     * 
     * @param grid the new puzzle's grid
     */
    public void setGrid(YGrid grid) {
        this.grid = grid;
    }

    /**
     * Gets the circles in this puzzle, so as to iterate over them.
     *
     * @return the hints / circles of the puzzle
     */
    public int[] getCircles() {
        return circles;
    }

    /**
     * Sets the selected circle by its value.
     *
     * @param value the value of the circle to select
     */
    public int getIndexSelectedCircle(final int value) {
        for (int i = 0; i < circles.length; i++) {
            if (circles[i] == value) {
                return i;
            }
        }
        throw new IllegalArgumentException("Circle with value " + value + " not found.");
    }

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

    public int getRowCount() {
        return 3;
    }

    public int getColumnCount() {
        return 3;
    }

    /**
     * Returns a YCell given a row and a column (for the paintComponent).
     * 
     * @param r row number
     * @param c column number
     * @return YCell
     */
    public YCell getCell(int r, int c) {
        int index = 3 * r + c;
        return grid.getCell(index);
    }

    /**
     * Returns all cells in the grid as a list.
     * 
     * @return List of all cells in the grid
     */
    public List<YCell> getCells() {
        List<YCell> cells = new ArrayList<>();
        for (int r = 0; r < getRowCount(); r++) {
            for (int c = 0; c < getColumnCount(); c++) {
                cells.add(getCell(r, c));
            }
        }
        return cells;
    }

    /**
     * Fills the next empty cell in the grid with the number suggested.
     * Iterates over the grid row by row, then column by column.
     * When it finds a cell with a value of 0 (indicating it's empty), it sets the
     * cell's value to 1.
     * If all cells are filled (none have a value of 0), this method does nothing.
     */
    public void fillNextNumber(int hint) {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                YCell cell = getCell(i, j);
                if (cell.isEmpty()) {
                    cell.setState(hint);
                    return;
                }
            }
        }
    }

    /** Gets the list of cell locations that arent equal to the sum. */
    public List<YCell> getViolatedCells() {
        List<YGroup> groups = grid.getGroups();
        List<YCell> violated = new ArrayList<>();
        for (YGroup yg : groups) {
            if (!yg.equalsExpectedSum() && yg.isFull()) {
                violated.addAll(yg.getCells());
            }
        }
        return violated;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Mode: ").append(mode).append("\n");
        sb.append(grid.toString()).append("\n");

        sb.append("Circles:\n");
        sb.append(circles[0]).append(" ").append(circles[1]).append("\n");
        sb.append(circles[2]).append(" ").append(circles[3]).append("\n");

        return sb.toString();
    }

}
