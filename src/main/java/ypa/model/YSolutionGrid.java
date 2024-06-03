package ypa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A 3x3 grid of cells for a Kakuro puzzle,
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
    
    public void setCell(int position, int value) {
        grid.get(position).setState(value);
    }

    public boolean isValid() {        
        for (YCell cell : grid) {
            if (!cell.isValid()) {
                return false;
            }
        }
        
        HashSet<Integer> seen = new HashSet<>();

        for (YCell cell : grid) {
            int value = cell.getState();
            if (value < -1 || value > 9) {
                return false;
            }
            
            // Check for duplicates between 1 and 9
            if (value != -1 && value != 0) {
                if (!seen.add(value)) {
                    return false;
                }
            }
        }

        
        return true;
    }
    
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
