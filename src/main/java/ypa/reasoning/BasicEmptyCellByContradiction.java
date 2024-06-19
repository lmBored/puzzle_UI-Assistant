package ypa.reasoning;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.YCell;
import ypa.model.YPuzzle;

import ypa.model.*;

/**
 * When only one way of filling an empty cell does not lead to an invalid state,
 * then that one way of filling is forced.
 * This is slightly more general (and possibly more costly) than
 * the {@link EntryWithOneEmptyCell} reasoner.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class BasicEmptyCellByContradiction extends EmptyCellReasoner {

    public BasicEmptyCellByContradiction(YPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(final YCell cell) throws NullPointerException {
        CompoundCommand result = super.applyToCell(cell);
        Command candidateForcedCommand = null; // command that worked, if any

        for (int state = 1; state <= 9; ++state) {
            Command command = new SetCommand(cell, state);
            command.execute();
            boolean valid = puzzle.isValid();
            command.revert();
            if (valid) {
                // no contraction; command is a candidate
                if (candidateForcedCommand == null) {
                    // first command that is valid; memorize it
                    candidateForcedCommand = command;
                } else {
                    // multiple valid ways of filling cell; no forced command
                    result.add(command); // Add the command to the result
                    return result;
                }
            }
        }
        // at most one command worked

        if (candidateForcedCommand == null) {
            // all commands failed: puzzle not solvable
            return null;
        } else {
            // exactly one command worked
            result.add(candidateForcedCommand);
            return result;
        }
    }

}