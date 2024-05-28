package kpa.model;

import java.util.HashMap;

/**
 * A histogram of cell states, counting how often each state occurs in a group.
 *
 * @inv For each possible cell state, a count is maintained
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class Histogram extends HashMap<Integer, Integer> {

    /** Constructs an empty histogram. */
    public Histogram() {
        super();
        put(KCell.BLOCKED, 0);
        put(KCell.EMPTY, 0);
    }

    /**
     * Returns the occurrence count for a given cell state.
     *
     * @param key  the given cell state
     * @return how often {@code key} occurs
     */
    @Override
    public Integer get(Object key) {
        Integer current = super.get(key);
        if (current == null) {
            current = 0;
        }
        return current;
    }

    /**
     * Adjusts the count for a given state.
     *
     * @param state  state whose count changes
     * @param delta  the amount of change
     * @pre {@code true}
     * @modifies {@code this}
     * @post {@code get(state) == \old(get(state) + delta) &&}<br>
     *   {@code (\forall CellState s; s != state; get(s) == \old(get(s)))}
     */
    public void adjust(final int state, final int delta) {
        put(state, get(state) + delta);
    }

}
