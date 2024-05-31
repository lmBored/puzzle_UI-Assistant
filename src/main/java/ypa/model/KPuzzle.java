package ypa.model;

import java.util.Scanner;

/**
 * State of a Kakuro puzzle, without auxiliary solver-related information:
 * the collection of entries, and the states of the cells in the grid
 * (mutable).
 * Acts as a facade for the underlying classes.
 * A Kakuro puzzle has
 * <ul>
 * <li>a name;
 * <li>minimum and maximum value for cell states,
 *   the minimum is at least one, and the maximum is at least the minimum;
 * <li>a mode of operation;
 * <li>a grid of cells.
 * </ul>
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class KPuzzle {

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

    /** Smallest number allowed.{@code KCell.EMPTY < minNumber}. */
    private int minNumber = 1;

    /** Largest number allowed.{@code minNumber <= maxNumber}. */
    private int maxNumber = 9;

    /** The grid of cells. */
    private final KGrid grid;

    /**
     * Constructs a new puzzle with initial state read from given scanner,
     * and with a given name.
     * The actual dimensions are determined from the input.
     *
     * @param scanner  the given scanner
     * @param name  the given name
     */
    public KPuzzle(final Scanner scanner, final String name) {
        this.name = name;
        this.mode = Mode.VIEW;
        this.grid = new KGrid(scanner);
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the name of this puzzle.  Only allowed in edit mode.
     *
     * @param name  the new name
     * @throws IllegalStateException  if not in edit mode
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
     * Sets the mode of this puzzle.
     *
     * @param mode the new mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Gets the smallest number allowed in a cell.
     *
     * @return the smallest number allowed in a cell
     */
    public int getMinNumber() {
        return minNumber;
    }

    /**
     * Sets the smallest number allowed in a cell.
     * Only allowed in edit mode.
     *
     * @param minNumber the new minimum number
     * @throws IllegalStateException  if not in edit mode
     * @throws IllegalArgumentException  if {@code minNumber < 1}
     * @pre puzzle is in edit mode and {@code minNumber >= 1}
     */
    public void setMinNumber(int minNumber) {
        if (mode != Mode.EDIT) {
            throw new IllegalStateException(this.getClass().getSimpleName()
                    + ".setMinNumber(): not in EDIT mode");
        }
        if (minNumber < 1) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + ".setMinNumber.pre failed: minNumber == "
                    + minNumber + " <= " + KCell.EMPTY);
        }
        this.minNumber = minNumber;
    }

    /**
     * Gets the largest number allowed in a cell.
     *
     * @return the largest number allowed in a cell
     */
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * Sets the largest number allowed in a cell.
     * Only allowed in edit mode.
     *
     * @param maxNumber the new maximum number
     * @throws IllegalStateException  if not in edit mode
     * @throws IllegalArgumentException  if {@code maxNumber < getMinNumber()}
     * @pre puzzle is in edit mode and {@code maxNumber >= getMinNumber()}
     */
    public void setMaxNumber(int maxNumber) {
        if (mode != Mode.EDIT) {
            throw new IllegalStateException(this.getClass().getSimpleName()
                    + ".setMaxNumber(): not in EDIT mode");
        }
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + ".setMaxNumber.pre failed: maxNumber == "
                    + maxNumber + " < " + minNumber + " == minNumber");
        }
        this.maxNumber = maxNumber;
    }

    /**
     * Checks whether a given number is valid.
     *
     * @param n  the number to check
     * @return whether {@code n} is valid
     */
    public boolean isValidNumber(int n) {
        return minNumber <= n && n <= maxNumber;
    }

    /**
     * Checks whether the current state of the puzzle is valid.
     * That is, whether it adheres to the rules:
     * <ul>
     * <li>Each non-blocked cell is either empty or contains a valid number
     *   (see {@link kpa.model.KPuzzle#isValidNumber(int)})</li>
     * <li>For each (horizontal or vertical) entry, the numbers appearing in it
     *   are all distinct</li>
     * <li>For each (horizontal or vertical) entry with at least one empty cell,
     *   the sum of the numbers it contains sums to less than the specified total
     * <li>For each (horizontal or vertical) entry without empty cells,
     *   the sum of the numbers it contains equals the specified total
     * </ul>
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
    public Iterable<KCell> getCells() {
        return grid;
    }

    /**
     * Gets the number of columns in this puzzle.
     *
     * @return number of columns
     */
    public int getColumnCount() {
        return grid.getColumnCount();
    }

    /**
     * Gets the number of rows in this puzzle.
     *
     * @return number of rows
     */
    public int getRowCount() {
        return grid.getRowCount();
    }

    /**
     * Returns whether a coordinate pair is in the puzzle's grid.
     *
     * @param rowIndex  the row index
     * @param columnIndex  the column index
     * @return whether {@code (row, column)} belongs to the grid
     */
    public boolean has(final int rowIndex, final int columnIndex) {
        return 0 <= rowIndex && rowIndex < grid.getRowCount()
                && 0 <= columnIndex && columnIndex < grid.getColumnCount();
    }

    /**
     * Gets the cell at given coordinates.
     *
     * @param rowIndex  the row coordinate to get from
     * @param columnIndex  the column coordinate to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre {@code 0 <= rowIndex < getRows() &&
     *   0 <= columnIndex < getColumns()}
     * @post {@code \result = cells[rowIndex, columnIndex]}
     */
    public KCell getCell(final int rowIndex, final int columnIndex) {
        if (!has(rowIndex, columnIndex)) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + ".getCell().pre failed: invalid location");
        }
        return grid.getCell(rowIndex, columnIndex);
    }

    /**
     * Gets the entries in this puzzle, so as to iterate over them.
     *
     * @return the entries of this puzzle as iterable
     */
    public Iterable<KEntry> getEntries() {
        return grid.getEntries();
    }

    /**
     * Gets number of cells with a given state.
     *
     * @param state  the given state
     * @return number of cells with state {@code state}
     * @post {@code \result == (\num_of Cell cell : getCells();
     *   cell.getState() == state)}
     */
    public int getStateCount(final int state) {
        return grid.getStateCount(state);
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

    /**
     * Makes a descriptor for an empty puzzle of given size.
     *
     * @param size  the given size
     * @return descriptor string for empty puzzle of {@code size}
     */
    public static String makeEmptyDescriptor(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException(KPuzzle.class.getSimpleName()
                    + ".makeEmptyDescriptor().pre failed: "
                    + "size == " + size + " < 0");
        }
        return "Undefined Kakuro puzzle state with size " + size;
    }

    public String gridAsString() {
        return grid.gridAsString();
    }

    @Override
    public String toString() {
        return grid.toString();
    }

}
