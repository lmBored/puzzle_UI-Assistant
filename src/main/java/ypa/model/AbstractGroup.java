package ypa.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract base class for a group of related cells
 * in the grid of a puzzle.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public abstract class AbstractGroup implements Iterable<KCell> {

    /** The cells in the group. */
    private final List<KCell> cells;

    /** How often each cell's state occurs in the group. */
    private final Histogram counts;

    /** Total value in all non-blocked cells. */
    private int total;

    // Private invariants
    //   (\forall state : CellState.values();
    //     counts[state] == (\num_of i; cells.has(i);
    //       cells.get(i).getState == state))

    /**
     * Constructs an empty group.
     */
    public AbstractGroup() {
        cells = new ArrayList<>();
        counts = new Histogram();
        total = 0;
    }

    /**
     * Gets the total value of all cells in the group.
     *
     * @return {@code (\sum cell : this; cell.getState())}
     */
    public int getTotal() {
        return total;
    }

    /**
     * Returns whether this group contains a given cell.
     *
     * @param cell  the cell to check
     * @return whether {@code this} contains {@code cell}
     */
    public boolean contains(final KCell cell) {
        return cells.contains(cell);
    }

    /**
     * Adds a cell to this group.
     *
     * @param cell  the cell to add
     * @pre {@code cell != null}
     * @post {@code this == \old(this + [cell])}
     */
    void add(final KCell cell) {
        cells.add(cell);
        int state = cell.getState();
        counts.adjust(state, +1);
        if (state != KCell.BLOCKED) {
            total += state;
        }
    }

    /**
     * Updates this group when a cell is about to change state.
     *
     * @param cell  the cell that triggered the update, in its old state
     * @param newState  the new state for {@code cell}
     * @pre {@code cell != null}
     * @post {@code counts have been updated}
     */
    public void update(final KCell cell, final int newState) {
        int state = cell.getState();
        counts.adjust(state, -1);
        counts.adjust(newState, +1);
        total += -state + newState;
    }

    /**
     * Gets number of cells in this group.
     *
     * @return number of cells in {@code this}
     * @post {@code \result == (\num_of Cell cell; contains(cell); true)}
     */
    public int getCount() {
        return cells.size();
    }

    /**
     * Gets number of cells with a given state.
     *
     * @param state  the given state
     * @return number of cells with state {@code state}
     * @post {@code \result == (\num_of Cell cell; contains(cell);
     *   cell.getState() == state)}
     */
    public int getStateCount(final int state) {
        return counts.get(state);
    }

    /**
     * Returns whether this group is valid, according to the rules.
     * A state is called valid, when it can be extended into a solution,
     * by appropriately filling the remaining empty cells.
     *
     * @return whether {@code this} is a valid group
     */
    public abstract boolean isValid();

    /**
     * Returns an iterator over this group.
     */
    @Override
    public Iterator<KCell> iterator() {
        return cells.iterator();
    }

}
