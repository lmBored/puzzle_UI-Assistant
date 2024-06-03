package ypa.model;

/**
 * A grid cell for a Sujiko puzzle.
 *
 */
public class YCell {

    /** The blocked state. */
    public static final int BLOCKED = -1;

    /** The empty state. */
    public static final int EMPTY = 0;
    
    /** String for blocked state. */
    public static final String BLOCKED_STR = "\\";

    /** String for empty state. */
    public static final String EMPTY_STR = ".";

    /** The cell's state. */
    private int state;

    /**
     * Constructs a cell with a given state.
     *
     * @param state  state
     * @pre {@code state} is a valid state
     */
    public YCell(final int state) {
        if (state < BLOCKED) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "(" + state + ").pre failed");
        }
        this.state = state;
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
        if (state < BLOCKED) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + ".setState().pre failed: state == " + state + " < " + BLOCKED);
        }
        
        this.state = state;
    }

    /**
     * Returns whether cell is blocked.
     *
     * @return whether {@code this} is blocked
     */
    public boolean isBlocked() {
        return state == BLOCKED;
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
    public boolean isOK() {
        if (!this.isFilled()) {
            return true;
        }
        
        return state < 10;
    }

    @Override
    public String toString() {
        return switch (state) {
            case BLOCKED -> BLOCKED_STR;
            case EMPTY -> EMPTY_STR;
            default -> String.valueOf(state);
        };
    }

}
