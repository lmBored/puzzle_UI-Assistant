package ypa.solvers;

import ypa.command.Command;
import ypa.command.SetCommand;
import ypa.model.YCell;
import ypa.model.YPuzzle;
import ypa.model.YGrid;
import ypa.reasoning.Reasoner;

/**
 * YBacktrackSolver is a solver for Sujiko puzzles using a backtracking
 * approach.
 * Tries to fill the grid so that circle values are the sum of their surrounding
 * cells.
 * It can use a reasoning strategy before attempting to solve the puzzle via
 * backtracking.
 */
public class YBacktrackSolver extends YAbstractSolver {

    /** The strategy to apply before speculating; null if no reasoner. */
    protected Reasoner reasoner;

    /** The grid of cells. */
    private final YGrid grid;

    
    /** The grid of cells in the background. */
    public YGrid backgroundGrid = null;
    
    /** The array of 4 numbers representing the 4 given hints. */
    private final int[] circles;

    /**
     * Constructs a backtracking solver for a given puzzle.
     *
     * @param puzzle the puzzle
     * @throws IllegalArgumentException if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public YBacktrackSolver(YPuzzle puzzle, final Reasoner reasoner) {
        super(puzzle);
        this.reasoner = reasoner;
        this.grid = puzzle.getGrid();
        this.circles = puzzle.getCircles();
    }

    /**
     * Attempts to solve the Sujiko puzzle.
     *
     * @return {@code true} if a solution is found, {@code false} otherwise
     */
    @Override
    public boolean solve() {
        this.backgroundGrid = this.grid.clone();
        YGrid solution = sujikoSolver(circles, backgroundGrid);
        return solution != null;
    }

    /**
     * Solves the Sujiko puzzle given the circle sums.
     *
     * @param circles array of 4 integers representing the sums of the numbers
     *                in each circle
     * @param grid    the initially empty 3x3 grid that will contain the solutions
     * @return YGrid of 9 integers with the solution grid or
     *         {@code null} if no solution exists
     */
    public static YGrid sujikoSolver(int[] circles,
            YGrid grid) {
        if (backtrack(grid, circles, 0)) {
            return grid;
        }
        return null;
    }

    /**
     * Recursively attempts to fill the Sujiko grid using backtracking.
     *
     * @param grid    the current grid being filled
     * @param circles an array of 4 integers representing the sums of the
     *                numbers in each circle
     * @param used    an array to track which numbers have been used in the grid
     * @param index   the current index in the grid being filled
     * @return {@code true} if a solution is found, {@code false} otherwise
     */
    private static boolean backtrack(YGrid grid,
            int[] circles, int index) {
        if (index == 9) {
            return checkSums(circles, grid);
        }

        for (int num = 1; num <= 9; num++) {
            if (!isUsed(grid, num)) {
                grid.setCell(index, num);
                if (backtrack(grid, circles, index + 1)) {
                    return true;
                }
                grid.setCell(index, YCell.EMPTY);
            }
        }
        return false;
    }

    private static boolean isUsed(YGrid grid, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid.getValue(i) == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSums(int[] circles, YGrid grid) {
        return (grid.getValue(0) + grid.getValue(1) + grid.getValue(3)
                + grid.getValue(4) == circles[0])
                && (grid.getValue(1) + grid.getValue(2) + grid.getValue(4)
                        + grid.getValue(5) == circles[1])
                && (grid.getValue(3) + grid.getValue(4) + grid.getValue(6)
                        + grid.getValue(7) == circles[2])
                && (grid.getValue(4) + grid.getValue(5) + grid.getValue(7)
                        + grid.getValue(8) == circles[3]);
    }

    
    /**
     * Gets the cells in this puzzle, so as to iterate over them.
     *
     * @return the cells of this puzzle as iterable
     */
    public YGrid getBackgroundGrid() {
        return this.backgroundGrid;
    }
   
}

    public boolean isSolvable() {
        YGrid gridCopy = new YGrid(this.grid);
    
        if (backtrack(gridCopy, this.circles, 0)) {
            return true;
        }
        return false;
    }

}
