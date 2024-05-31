package ypa.solvers;

import ypa.command.Command;
import ypa.command.SetCommand;
import ypa.model.KCell;
import ypa.model.KPuzzle;
import ypa.reasoning.Reasoner;

/**
 * SujikoBacktrackingSolver is a solver for Sujiko puzzles using a backtracking approach.
 * Tries to fill the grid so that circle values are the sum of their surrounding cells.
 * It can use a reasoning strategy before attempting to solve the puzzle via backtracking.
 */
public class SujikoBacktrackingSolver extends AbstractSolver {
    
    /** The strategy to apply before speculating; null if no reasoner. */
    protected Reasoner reasoner;

    /* Rep. invariant:
     *  reasoner != null ==> reasoner.puzzle == this.puzzle
     */

    /**
     * Constructs a backtracking solver for a given puzzle.
     *
     * @param puzzle  the puzzle
     * @throws IllegalArgumentException  if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public SujikoBacktrackingSolver(KPuzzle puzzle, final Reasoner reasoner) {
        super(puzzle);
        this.reasoner = reasoner;
    }
    
    /**
     * Attempts to solve the Sujiko puzzle.
     *
     * @return {@code true} if a solution is found, {@code false} otherwise
     */
    @Override
    public boolean solve() {        
        int[] circles = {22, 15, 19, 15}; // Input will have to be chaanged according to the puzzle
        int[] solution = sujikoSolver(circles); 
        // solution will be an array of 9 elements with a possible solution of the puzzle
        if (solution != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Solves the Sujiko puzzle given the circle sums.
     *
     * @param circles array of 4 integers representing the sums of the numbers in each circle
     * @return array of 9 integers with the solution grid or {@code null} if no solution exists
     */
    public static int[] sujikoSolver(int[] circles) {
        int[] grid = new int[9];
        boolean[] used = new boolean[10];
        if (backtrack(grid, circles, used, 0)) {
            return grid;
        }
        return null;
    }
    
    /**
     * Recursively attempts to fill the Sujiko grid using backtracking.
     *
     * @param grid the current grid being filled
     * @param circles an array of 4 integers representing the sums of the numbers in each circle
     * @param used an array to track which numbers have been used in the grid
     * @param index the current index in the grid being filled
     * @return {@code true} if a solution is found, {@code false} otherwise
     */
    private static boolean backtrack(int[] grid, int[] circles, boolean[] used, int index) {
        if (index == 9) {
            return checkSum(grid, circles);
        }

        for (int num = 1; num <= 9; num++) {
            if (!used[num]) {
                grid[index] = num;
                used[num] = true;
                if (backtrack(grid, circles, used, index + 1)) {
                    return true;
                }
                grid[index] = 0;
                used[num] = false;
            }
        }
        return false;
    }
    
    /**
     * Checks if the current grid configuration matches the circle sums.
     *
     * @param grid the current grid being validated
     * @param circles an array of 4 integers representing the sums of the numbers in each circle
     * @return {@code true} if the grid configuration is valid, {@code false} otherwise
     */
    private static boolean checkSum(int[] grid, int[] circles) {
        int[][] positions = {
            {0, 1, 3, 4},
            {1, 2, 4, 5},
            {3, 4, 6, 7},
            {4, 5, 7, 8}
        };

        for (int i = 0; i <= 3; i++) {
            int sum = 0;
            for (int pos : positions[i]) {
                sum += grid[pos];
            }
            if (sum != circles[i]) {
                return false;
            }
        }
        return true;
    }
}


