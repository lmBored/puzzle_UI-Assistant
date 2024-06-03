package ypa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A 3x3 grid of cells for a Sujiko puzzle,
 * representing the solution grid.
 *
 */
public class YSolutionGrid {
    
    /** The grid of cells as a list of 9 numbers. */
    private final List<YCell> grid = new ArrayList<>();
    
    /**
     * Constructor for an empty grid.
     */
    public YSolutionGrid() {
        for (int i = 0; i < 9; i++) {
            grid.add(new YCell(YCell.EMPTY));
        }
    }
    
    /**
     * Constructor that initializes the grid with a list of integers.
     * Each integer represents the state of a cell in the grid.
     *
     * @param initialStates the list of initial states for the cells.
     * @throws IllegalArgumentException if the list does not contain exactly 9 elements.
     */
    public YSolutionGrid(List<Integer> initialStates) {
        if (initialStates == null || initialStates.size() != 9) {
            throw new IllegalArgumentException("Initial states must contain exactly 9 elements.");
        }
        for (int state : initialStates) {
            grid.add(new YCell(state));
        }
    }
    
    public YCell getCell(int position) {
        return grid.get(position);
    }
    
    public int getValue(int position) {
        return grid.get(position).getState();
    }
    
    public void setCell(int position, int value) {
        grid.get(position).setState(value);
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

}
