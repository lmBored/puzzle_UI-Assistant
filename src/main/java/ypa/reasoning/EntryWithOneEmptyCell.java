package ypa.reasoning;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.AbstractGroup;
import ypa.model.YCell;
import ypa.model.YEntry;
import ypa.model.YPuzzle;
import ypa.model.YGroup;

/**
 * When all cells but one of an entry have been filled,
 * then the last empty cell remaining can be calculated.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class EntryWithOneEmptyCell extends EmptyCellReasoner {

    public EntryWithOneEmptyCell(YPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(YCell cell) throws NullPointerException {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "applyToCell.pre failed: cell is not empty");
        }
        CompoundCommand result = super.applyToCell(cell);

        for (AbstractGroup g : cell.groups()) {
            if (g instanceof YEntry && g.getStateCount(YCell.EMPTY) == 1) {
                // g is a horizontal or vertical entry with one empty cell
                int sum = ((YEntry) g).getSpecification().getSum();
                int newState = sum - g.getTotal();
                if (!puzzle.isValid()) {
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