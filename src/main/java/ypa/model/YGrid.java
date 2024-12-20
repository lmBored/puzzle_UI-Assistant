package ypa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A 3x3 grid of cells for a Sujiko puzzle,
 * representing the solution grid.
 *
 */
public class YGrid extends HashSet<YCell> {

    /** The grid of cells as a list of 9 numbers. */
    private final List<YCell> grid = new ArrayList<>();

    /** Groups of 4 cell and 1 circle. */
    private List<YGroup> groups = new ArrayList<>();
    
    private Set<Integer> filledCell;

    /**
     * Constructor for an empty grid.
     */
    public YGrid() {
        for (int i = 0; i < 9; i++) {
            grid.add(new YCell(YCell.EMPTY));
            grid.get(i).setLocation(i + 1);
        }

        for (int i = 0; i < 4; i++) {
            groups.add(new YGroup(i + 1));
        }
        // 1 2 4 5
        groups.get(0).addCell(grid.get(0));
        groups.get(0).addCell(grid.get(1));
        groups.get(0).addCell(grid.get(3));
        groups.get(0).addCell(grid.get(4));

        // 2 3 5 6
        groups.get(1).addCell(grid.get(1));
        groups.get(1).addCell(grid.get(2));
        groups.get(1).addCell(grid.get(4));
        groups.get(1).addCell(grid.get(5));
        // 4 5 7 8
        groups.get(2).addCell(grid.get(3));
        groups.get(2).addCell(grid.get(4));
        groups.get(2).addCell(grid.get(6));
        groups.get(2).addCell(grid.get(7));
        // 5 6 8 9
        groups.get(3).addCell(grid.get(4));
        groups.get(3).addCell(grid.get(5));
        groups.get(3).addCell(grid.get(7));
        groups.get(3).addCell(grid.get(8));

        filledCell = new HashSet<>();
    }

    /**
     * Constructor that initializes the grid with a list of integers.
     * Each integer represents the state of a cell in the grid.
     *
     * @param initialStates the list of initial states for the cells.
     * @throws IllegalArgumentException if the list does not contain exactly 9
     *                                  elements.
     */
    public YGrid(List<Integer> initialStates) {
        if (initialStates == null || initialStates.size() != 9) {
            throw new IllegalArgumentException("Initial states must contain exactly 9 elements.");
        }
        for (int state : initialStates) {
            grid.add(new YCell(state));
        }
        filledCell = new HashSet<>();
    }

    public YCell getCell(int position) {
        return grid.get(position);
    }

    public int getValue(int position) {
        return grid.get(position).getState();
    }

    public List<YGroup> getGroups() {
        return this.groups;
    }

    /**
     * Set the cell location within the grid.
     *
     * @param position the position of the cell.
     * @param value    the value of the cell.
     */
    public void setCell(int position, int value) {
        grid.get(position).setState(value);
    }

    /** Used for duplicate checking. */
    public boolean isValuePresent(int value) {
        filledCell.clear();
        for (YCell cell: grid) {
            if (cell.getState() != 0) {
                filledCell.add(cell.getState());
            }
        }
        return filledCell.contains(value);
    }

    /**
     * Checks whether this grid is valid.
     *
     * @return whether this is valid
     */
    public boolean isValid() {
        HashSet<Integer> seen = new HashSet<>();
        for (YCell cell : grid) {
            // Check if every value is between 0 and 9 (both included)
            if (!cell.isValid()) {
                return false;
            }

            // Check for duplicates between 1 and 9 (there shouldn't be any)
            int value = cell.getState();
            if (value > 0) {
                if (!seen.add(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether this grid is full (non empty values).
     *
     * @return whether this is full
     */
    public boolean isFull() {
        for (YCell cell : grid) {
            if (cell.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears grid and sets all values to empty.
     *
     */
    public void clear() {
        for (final YCell cell : grid) {
            cell.setState(YCell.EMPTY);
        }
    }

    /**
     * Converts this grid to a string.
     *
     * @return string representation of this
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < grid.size(); i++) {
            builder.append(grid.get(i).toString()).append(" ");
            if ((i + 1) % 3 == 0 && i != grid.size() - 1) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Copy constructor that initializes the grid with a copy of another YGrid.
     *
     * @param original the YGrid to copy.
     */
    public YGrid(YGrid original) {
        if (original == null) {
            throw new IllegalArgumentException("Original YGrid cannot be null.");
        }
        for (YCell cell : original.grid) {
            this.grid.add(new YCell(cell.getState()));
        }
    }

    /**
     * Creates a deep copy of this YGrid for the background worker.
     * 
     * @return a cloned instance of this YGrid.
     */
    @Override
    public YGrid clone() {
        List<Integer> initialStates = new ArrayList<>();
        for (YCell cell : this.grid) {
            initialStates.add(cell.getState());
        }
        return new YGrid(initialStates);
    }
}
