package kpa.reasoning;

import kpa.command.Command;
import kpa.command.CompoundCommand;
import kpa.command.SetCommand;
import kpa.model.AbstractGroup;
import kpa.model.KCell;
import kpa.model.KEntry;
import kpa.model.KPuzzle;

/**
 * When all cells but one of an entry have been filled,
 * then the last empty cell remaining can be calculated.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class EntryWithOneEmptyCell extends EmptyCellReasoner {

    public EntryWithOneEmptyCell(KPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(KCell cell) throws NullPointerException {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "applyToCell.pre failed: cell is not empty");
        }
        CompoundCommand result = super.applyToCell(cell);

        for (AbstractGroup g : cell.groups()) {
            if (g instanceof KEntry && g.getStateCount(KCell.EMPTY) == 1) {
                // g is a horizontal or vertical entry with one empty cell
                int sum = ((KEntry) g).getSpecification().getSum();
                int newState = sum - g.getTotal();
                if (!puzzle.isValidNumber(newState)) {
                    return null;
                }
                final Command command = new SetCommand(cell, newState);
                command.execute();
                final boolean valid = puzzle.isValid();
                command.revert();
                if (valid) {
                    result.add(command);
                    return result;
                } else {
                    return null;
                }
            }
        }

        return result;
    }

}
