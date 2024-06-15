package ypa.solvers;

import java.util.Collection;
import java.util.HashSet;
import javax.swing.SwingWorker;
import ypa.command.Command;
import ypa.model.YGrid;
import ypa.model.YPuzzle;
import ypa.reasoning.Reasoner;

/**
 * This class represents a SwingWorker solving the puzzle in the background.
 * Extends SwingWorker to allow task execution in the background.
 * The solved puzzle is stored in solvedGrid.
 * The executed commands store in commands.
 * 
 * The puzzle solves using a YBacktrackSolver, optionally using a Reasoner.
 * 
 * @author Miquel
 */
public class SolverWorker extends SwingWorker<Void, Void> {

    private YPuzzle puzzle = null;
    //public YGrid solvedGrid = null;
    public Collection<Command> commands = null;
    Reasoner reasoner = null;
    public YBacktrackSolver solver = null;
    public boolean showSolution = false;

    /**
     * SolverWorker constructor.
     * 
     * @param puzzle The puzzle to solve.
     * @param reasoner The reasoner to use.
     */
    public SolverWorker(YPuzzle puzzle, Reasoner reasoner) {
        this.puzzle = puzzle;
        this.reasoner = reasoner;
        solver = new YBacktrackSolver(puzzle, reasoner);
    }

    /**
    * Background solving.
    * 
    * @return null.
    * @throws Exception if occurs any error during solving the puzzle.
    */
    @Override
    protected Void doInBackground() throws Exception {
        boolean solved = solver.solve();

        if (solved) {            
            this.commands = solver.getCommands();
            while (!showSolution) {
                Thread.sleep(1); // If I don't put this, it doesn't work
                // Wait until we want to see the solution (solve button clicked)
            }
            this.puzzle.setGrid(solver.getBackgroundGrid());
        }

        return null;
    }

    /**
    * Method called when the background task has been completed.
    */
    @Override
    protected void done() {

    }
}
