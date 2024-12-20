package ypa.solvers;

import ypa.command.Command;
import ypa.model.YPuzzle;
import ypa.model.YGrid;

import java.util.Collection;
import java.util.Stack;

/**
 * Abstract base class for solvers of Sujiko puzzles.
 *
 */
public abstract class YAbstractSolver {

    /** The puzzle being solved. */
    protected YPuzzle puzzle;

    /** Commands executed. */
    protected Stack<Command> commands;

    /**
     * Constructs a reasoner for a given puzzle.
     *
     * @param puzzle  the puzzle
     * @throws IllegalArgumentException  if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public YAbstractSolver(final YPuzzle puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "().pre failed: puzzle == null");
        }
        this.puzzle = puzzle;
        commands = new Stack<>();
    }

    /**
     * Gets the commands whose execution led to current puzzle state.
     *
     * @return commands executed to get to current puzzle state
     */
    public Collection<Command> getCommands() {
        return commands;
    }

    /**
     * Either finds one solution of the puzzle from its current state,
     * if solvable, or leaves the puzzle unchanged.
     *
     * @return whether puzzle was solved
     * @pre {@code puzzle != null}
     * @modifies {@code puzzle}
     * @post {@code
     *      (\result && puzzle.isSolved()) || (! \result && puzzle unchanged)}
     */
    public abstract boolean solve();

    public abstract boolean isSolvable();

    public abstract boolean isSolvableFromExisting(YGrid grid);

}
