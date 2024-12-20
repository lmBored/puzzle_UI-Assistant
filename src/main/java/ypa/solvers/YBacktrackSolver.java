package ypa.solvers;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.YCell;
import ypa.model.YPuzzle;
import ypa.model.YGrid;
import ypa.reasoning.Reasoner;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

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

    /** Flag to indicate if the solver should stop at the first solution. */
    private boolean stopAtFirstSolution = false;

    /** List to store all solutions if not stopping at the first solution. */
    private List<YGrid> solutions = new ArrayList<>();

    /** SwingWorker for running the solver in the background. */
    private SolverWorker solverWorker;

    /** Flag to indicate if the solver should be interrupted. */
    private volatile boolean interrupted = false;

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
        this.backgroundGrid = new YGrid(grid); // Initialize background grid
    }

    /**
     * Sets whether the solver should stop at the first solution.
     *
     * @param stopAtFirstSolution {@code true} to stop at the first solution,
     *                            {@code false} to find all solutions
     */
    public void setStopAtFirstSolution(boolean stopAtFirstSolution) {
        this.stopAtFirstSolution = stopAtFirstSolution;
    }

    /**
     * Starts solving the puzzle in the background.
     */
    public void startSolving() {
        solverWorker = new SolverWorker();
        solverWorker.execute();
    }

    /**
     * Interrupts the solver.
     */
    public void interrupt() {
        interrupted = true;
        if (solverWorker != null) {
            solverWorker.cancel(true);
        }
    }

    /**
     * Inner class to handle background execution using SwingWorker.
     */
    private class SolverWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            if (reasoner != null) {
                CompoundCommand command = reasoner.apply();
                if (command != null && command.size() > 0) {
                    command.execute();
                }
            }

            if (stopAtFirstSolution) {
                YGrid solution = sujikoSolver(circles, backgroundGrid);
                if (solution != null) {
                    solutions.add(solution);
                }
            } else {
                findAllSolutions(backgroundGrid, circles, 0);
            }
            return null;
        }

        @Override
        protected void done() {
            // Handle completion (e.g., update UI or notify the user)
        }
    }

    /**
     * Finds all solutions to the Sujiko puzzle using backtracking.
     *
     * @param grid    the current grid being filled
     * @param circles an array of 4 integers representing the sums of the
     *                numbers in each circle
     * @param index   the current index in the grid being filled
     */
    private void findAllSolutions(YGrid grid, int[] circles, int index) {
        if (index == 9) {
            if (checkSums(circles, grid)) {
                solutions.add(new YGrid(grid));
            }
            return;
        }

        for (int num = 1; num <= 9; num++) {
            if (interrupted) {
                return;
            }
            if (!isUsed(grid, num)) {
                grid.setCell(index, num);
                findAllSolutions(grid, circles, index + 1);
                grid.setCell(index, YCell.EMPTY);
            }
        }
    }

    /**
     * Returns the list of solutions found.
     *
     * @return the list of solutions
     */
    public List<YGrid> getSolutions() {
        return solutions;
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
    public static YGrid sujikoSolver(int[] circles, YGrid grid) {
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
     * @param index   the current index in the grid being filled
     * @return {@code true} if a solution is found, {@code false} otherwise
     */
    private static boolean backtrack(YGrid grid, int[] circles, int index) {
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

    /**
     * Checks whether the puzzle has at least one solution.
     *
     * @return true if the puzzle can be solved
     */
    public boolean isSolvable() {
        YGrid gridCopy = new YGrid(this.grid);

        if (backtrack(gridCopy, this.circles, 0)) {
            return true;
        }
        return false;
    }

    /**
     * Backtracks from an existing configuration of the grid to find a solution.
     * 
     * @param grid The grid to solve.
     * @param circles The array of circle values.
     * @param index The current index in the grid.
     * @return {@code true} if a solution is found, {@code false} otherwise.
     */
    private static boolean backtrackFromExisting(YGrid grid, int[] circles, int index) {
        if (index == 9) {
            return checkSums(circles, grid);
        }
    
        if (grid.getValue(index) != YCell.EMPTY) {
            return backtrackFromExisting(grid, circles, index + 1);
        }
    
        for (int num = 1; num <= 9; num++) {
            if (!isUsed(grid, num)) {
                grid.setCell(index, num);
                if (backtrackFromExisting(grid, circles, index + 1)) {
                    return true;
                }
                grid.setCell(index, YCell.EMPTY);
            }
        }
        return false;
    }

    /**
     * Determines if a puzzle can be solved from an existing grid configuration.
     * 
     * @param existingGrid The grid.
     * @return {@code true} if the puzzle is solvable from the existing grid, 
     *      {@code false} otherwise
     */
    public boolean isSolvableFromExisting(YGrid existingGrid) {
        YGrid gridCopy = new YGrid(existingGrid);
    
        if (backtrackFromExisting(gridCopy, this.circles, 0)) {
            return true;
        }
        return false;
    }
}
