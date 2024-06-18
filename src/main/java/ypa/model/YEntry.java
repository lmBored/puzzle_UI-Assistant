package ypa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry in a Sujiko puzzle, consisting of a group of cells
 * and an expected sum.
 * The expected sum is immutable.
 * The group of cells is mutable, and set cell-by-cell during construction.
 *
 * An entry traverses two phases:
 *
 * <ol>
 * <li>Constructor sets the expected sum.
 * <li>During puzzle set-up, cells are associated.
 * <li>During solving, all cells have been associated (see invariant Size),
 *   and should not change any more.
 * </ol>
 *
 * @inv NoBlocked: {@code (\forall cell : this; ! cell.isBlocked)}
 *
 * @inv <br>Size: {@code this.getCount() == 4}  // For Sujiko, each group has exactly 4 cells
 */
public class YEntry extends AbstractGroup {

    /** The expected sum for this entry. */
    private final int expectedSum;

    /**
     * Constructs a {@code YEntry} from a given expected sum.
     *
     * @param expectedSum the given expected sum
     */
    public YEntry(final int expectedSum) {
        this.expectedSum = expectedSum;
    }

    /**
     * Constructs a {@code YEntry} from a given scanner.
     *
     * @param scanner  the given scanner
     */
    public YEntry(final Scanner scanner) {
        this.expectedSum = scanner.nextInt();
    }

    public int getExpectedSum() {
        return expectedSum;
    }

    /**
     * Adds a given empty cell.
     *
     * @param cell  the cell to add
     * @pre {@code cell.isEmpty()}
     */
    @Override
    public void add(final YCell cell) {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "add().pre failed: cell is not empty");
        }
        super.add(cell);
    }

    @Override
    public boolean isValid() {
        for (YCell cell : this) {
            if (!cell.isEmpty() && this.getStateCount(cell.getState()) > 1) {
                // digit occurs more than once
                return false;
            }
        }
        final int total = this.getTotal();
        final int emptyCount = this.getStateCount(YCell.EMPTY);
        if (total + emptyCount > expectedSum) {
            // sum of digits filled in is too high
            return false;
        }
        return emptyCount != 0 || total == expectedSum;
    }

    @Override
    public String toString() {
        return String.format("Expected Sum: %d", expectedSum);
    }

    /**
     * Returns a verbose string representation.
     *
     * @return verbose string representation of {@code this}
     */
    public String toStringLong() {
        return "{ expectedSum: " + expectedSum + " }";
    }

    /**
     * Reads a list of entries from a given scanner.
     *
     * @param scanner the given scanner
     * @return the scanned list of entries
     * @post white space has been skipped on scanner
     */
    public static List<YEntry> scanEntries(final Scanner scanner) {
        List<YEntry> result = new ArrayList<>();
        while (scanner.hasNextInt()) {
            try {
                result.add(new YEntry(scanner));
            } catch (Exception e) {
                throw new IllegalArgumentException(YEntry.class.getSimpleName()
                        + ".scanEntries(Scanner).pre failed: after " + result.size() + " entries");
            }
        }
        return result;
    }

    public YEntry getSpecification() {
        return this;
    }

    public int getSum() {
        return expectedSum;
    }

    public int getLength() {
        return 4;
    }
}
