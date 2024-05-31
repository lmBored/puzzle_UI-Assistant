package ypa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Entry in a Kakuro puzzle, consisting of a location, direction,
 * a specification, and a group of cells.
 * The location, direction, and specification are immutable.
 * The group of cells is mutable, and set cell-by-cell during construction.
 *
 * An entry traverses two phases:
 *
 * <ol>
 * <li>Constructor sets location, direction, and specification.
 * <li>During puzzle set-up, cells are associated.
 * <li>During solving, all cells have been associated (see invariant Size),
 *   and should not change any more.
 * </ol>
 *
 * @inv NoBlocked: {@code (\forall cell : this; ! cell.isBlocked)}
 *
 * @inv <br>Size: {@code this.getCount() == this.specification.getLength()}
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class KEntry extends AbstractGroup {

    /** The location. */
    private final Location location;

    /** The direction. */
    private final Direction direction;

    /** The specification. */
    private final KSpec specification;

    /**
     * Constructs a {@code KEntry} from a given location, direction, and
     * specification.
     *
     * @param location  the given location
     * @param direction  the given direction
     * @param specification  the given specification
     */
    public KEntry(final Location location, final Direction direction, final KSpec specification) {
        this.location = location;
        this.direction = direction;
        this.specification = specification;
    }

    /**
     * Constructs a {@code KEntry} from a given scanner.
     *
     * @param scanner  the given scanner
     */
    public KEntry(final Scanner scanner) {
        this.location = new Location(scanner);
        this.direction = Direction.fromScanner(scanner);
        this.specification = new KSpec(scanner);
    }

    public Location getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }

    public KSpec getSpecification() {
        return specification;
    }

    /**
     * Adds a given empty cell.
     *
     * @param cell  the cell to add
     * @pre {@code cell.isEmpty()}
     */
    @Override
    public void add(final KCell cell) {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "add().pre failed: cell is not empty");
        }
        super.add(cell);
    }

    @Override
    public boolean isValid() {
        for (KCell cell : this) {
            // these cells are not blocked
            if (!cell.isEmpty() && this.getStateCount(cell.getState()) > 1) {
                // digit occurs more than once
                return false;
            }
        }
        final int total = this.getTotal();
        final int emptyCount = this.getStateCount(KCell.EMPTY);
        final int expectedSum = this.specification.getSum();
        if (total + emptyCount > expectedSum) {
            // sum of digits filled in is too high
            // N.B. empty cells will contain at least a 1
            return false;
        }
        return emptyCount != 0 || total == expectedSum;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", location, direction, specification);
    }

    /**
     * Returns a verbose string representation.
     *
     * @return verbose string representation of {@code this}
     */
    public String toStringLong() {
        return "{ location: " + location
                + "direction" + direction
                + "specification" + specification
                + " }";
    }

    /**
     * Reads a list of entries from a given scanner.
     *
     * @param scanner the given scanner
     * @return the scanned list of entries
     * @post white space has been skipped on scanner
     */
    static List<KEntry> scanEntries(final Scanner scanner) {
        List<KEntry> result = new ArrayList<>();
        Pattern original = scanner.delimiter();
        scanner.skip("\\p{javaWhitespace}*");
        scanner.useDelimiter("");
        while (scanner.hasNext("[a-zA-Z]")) {
            scanner.useDelimiter(original);
            try {
                result.add(new KEntry(scanner));
            } catch (Exception e) {
                throw new IllegalArgumentException(KEntry.class.getSimpleName()
                        + ".scanEntries(Scanner).pre failed: after " + result.size() + " entries");
            }
            scanner.skip("\\p{javaWhitespace}*");
            scanner.useDelimiter("");
        }
        scanner.useDelimiter(original);
        return result;
    }

    /**
     * Determine minimum number of rows and columns of a list of
     * {@code KEntry} items.
     *
     * @param entries  the list
     * @return  {@code \result.getRow() == number of rows} and
     *     {@code \result.getColumn() == number of columns}
     */
    public static Location dimensions(final List<KEntry> entries) {
        int maxRow = -1; // largest row coordinate encountered
        int maxColumn = -1; // largest column coordinate encountered
        for (KEntry entry : entries) {
            final int row = entry.getLocation().getRow();
            final int column = entry.getLocation().getColumn();
            final int length = entry.getSpecification().getLength();
            final int rowEnd = row + length;
            final int columnEnd = column + length;
            switch (entry.getDirection()) {
                case HORIZONTAL -> {
                    if (row > maxRow) {
                        maxRow = row;
                    }
                    if (columnEnd > maxColumn) {
                        maxColumn = columnEnd;
                    }
                }
                case VERTICAL -> {
                    if (rowEnd > maxRow) {
                        maxRow = rowEnd;
                    }
                    if (column > maxColumn) {
                        maxColumn = column;
                    }
                }
                default -> throw new IllegalStateException("unknown Direction: "
                        + entry.getDirection());
            }
        }
        return new Location(maxRow, maxColumn);
    }

}
