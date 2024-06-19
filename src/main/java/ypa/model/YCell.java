package ypa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A grid cell for a Sujiko puzzle.
 *
 */
public class YCell {

    /** The blocked state. */
    public static final int BLOCKED = -1;

    /** The empty state. */
    public static final int EMPTY = 0;

    /** String for empty state. */
    public static final String EMPTY_STR = ".";

    /** The cell's state. */
    private int state;

    private int location;

    /** The 2 groups which the cell belongs to. **/
    private List<AbstractGroup> groups;
    
    /**
     * Constructs a cell with a given state.
     *
     * @param state  state
     * @pre {@code state} is a valid state
     */
    public YCell(final int state) {
        if (state < EMPTY || state > 9) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "(" + state + ").pre failed");
        }
        this.state = state;
        groups = new ArrayList<>();
    }

    public int getState() {
        return state;
    }

    /**
     * Sets a new cell state.
     *
     * @param state  the new state
     * @pre {@code state} is valid
     */
    public void setState(int state) {
        if (state < EMPTY || state > 9) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + ".setState().pre failed: state == ");
        }
        
        this.state = state;
    }

    /**
     * Returns whether cell is empty.
     *
     * @return whether {@code this} is empty
     */
    public boolean isEmpty() {
        return state == EMPTY;
    }

    /**
     * Returns whether cell is filled.
     *
     * @return whether {@code this} is filled
     */
    public boolean isFilled() {
        return state > EMPTY;
    }

    /**
     * Returns whether this cell is involved in a rule violation.
     *
     * @return whether this cell conforms to the rules
     */
    public boolean isValid() {
        return EMPTY <= state && state <= 9;
    }

    public int getLocation() {
        return this.location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setGroup(YGroup group) {
        this.groups.add(group);
    }

    @Override
    public String toString() {
        return switch (state) {
            case EMPTY -> EMPTY_STR;
            default -> String.valueOf(state);
        };
    }

    public List<AbstractGroup> groups() {
        return groups;
    }

}
