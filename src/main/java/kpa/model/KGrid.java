package kpa.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

/**
 * A rectangular grid of cells for a Kakuro puzzle,
 * with the list of hints.  A hint consists of a number
 * that is the desired sum of the numbers appearing in
 * a group of horizontally or vertically adjacent cells.
 * Such a group is called an <em>entry</em>.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class KGrid extends AbstractGroup implements Iterable<KCell> {

    /** The grid of cells as a list of rows. */
    private final List<List<KCell>> matrix;

    /** Number of rows. */
    private final int nRows;

    /** Number of columns. */
    private final int nColumns;

    // TODO: Consider merging nRows, nColumns into EnumMap<Direction, Integer>

    /** The entry specifications. */
    private final List<KEntry> entries;

    /** Total sum and length of entries in a particular direction. */
    private final EnumMap<Direction, KSpec> totals;

    // Representation invariants:
    //   nRows == matrix.size()
    //   if nRows == 0
    //       then nColumns == 0
    //       else (\forall i; matrix.has(i); matrix.get(i).size() == nColumns)
    //   this.entries refers only to non-blocked cells in the matrix
    //   each empty cell occurs in exactly two entries,
    //      one horizontal and one vertical

    /**
     * Constructs a grid from a given scanner.
     *
     * @param scanner  the given scanner
     * @throws NullPointerException  if {@code scanner == null}
     * @throws IllegalArgumentException  if {@code scanner} does not yield
     *     a valid Kakuro puzzle
     * @pre {@code scanner != null} and it delivers a valid puzzle grid
     */
    public KGrid(final Scanner scanner) {
        matrix = new ArrayList<>();
        entries = KEntry.scanEntries(scanner);

        // Initialize the grid to be just big enough to contain all entries.

        // 1. Find out the required dimensions.
        final Location dim = KEntry.dimensions(entries);
        nRows = dim.getRow();
        nColumns = dim.getColumn();

        // 2. Initialize the matrix to all blocked cells.
        for (int rowIndex = 0; rowIndex != nRows; ++rowIndex) {
            final List<KCell> row = new ArrayList<>();
            matrix.add(row);
            for (int columnIndex = 0; columnIndex != nColumns; ++columnIndex) {
                final KCell cell = new KCell(KCell.BLOCKED);
                cell.setGrid(this);
                cell.setLocation(new Location(rowIndex, columnIndex));
                row.add(cell);
                associate(cell, this);
            }
        }

        // 3. Define the cell states and grouping according to entries.
        totals = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            totals.put(direction, new KSpec(0, 0));
        }
        for (KEntry entry : entries) {
            // update totals
            KSpec old = totals.get(entry.getDirection());
            totals.put(entry.getDirection(),
                    new KSpec(
                            old.getSum() + entry.getSpecification().getSum(),
                            old.getLength()
                                    + entry.getSpecification().getLength()
                    )
            );
            Location loc = entry.getLocation();
            for (int i = 0; i != entry.getSpecification().getLength(); ++i) {
                final KCell cell = getCell(loc);
                // TODO: check that cell is not already covered in this direction
                cell.setState(KCell.EMPTY); // must be done before associate
                associate(cell, entry);
                loc = switch (entry.getDirection()) {
                    case HORIZONTAL -> new Location(loc.getRow(), loc.getColumn() + 1);
                    case VERTICAL -> new Location(loc.getRow() + 1, loc.getColumn());
                };
            }
        }

        if (!scanner.hasNext("=")) {
            return;
        }

        // Read states of non-blocked non-empty cells from scanner
        scanner.skip("=");
        while (scanner.hasNext()) {
            Location location = new Location(scanner);
            scanner.next("=");
            KCell cell = new KCell(scanner); // temporary cell to get state
            this.getCell(location).setState(cell.getState());
        }
    }

    /**
     * Gets the number of columns in this grid.
     *
     * @return number of columns
     */
    public int getColumnCount() {
        return nColumns;
    }

    /**
     * Gets the number of rows in this grid.
     *
     * @return number of rows
     */
    public int getRowCount() {
        return nRows;
    }

    /**
     * Gets the cell at given coordinates.
     *
     * @param rowIndex  the row coordinate to get from
     * @param columnIndex  the column coordinate to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre {@code 0 <= rowIndex < getRows() &&
     *   0 <= columnIndex < getColumns()}
     * @post {@code \result = cells[rowIndex, columnIndex]}
     */
    public KCell getCell(final int rowIndex, final int columnIndex) {
        return matrix.get(rowIndex).get(columnIndex);
    }

    /**
     * Gets the cell at given location.
     *
     * @param location  the location to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre location appears in grid
     * @post {@code \result == } cell at location
     */
    public KCell getCell(final Location location) {
        return getCell(location.getRow(), location.getColumn());
    }

    public List<KEntry> getEntries() {
        return entries;
    }

    /**
     * Puts a cell in a group.
     *
     * @param cell  the cell to put
     * @param group  the group to put into
     * @throws IllegalArgumentException  if precondition violated
     * @pre {@code cell != null && group != null &&
     *   ! cell.isElementOf(group) && ! group.contains(cell)}
     * @post {@code group.contains(cell)}
     */
    public static void associate(
            final KCell cell, final AbstractGroup group)
            throws IllegalArgumentException {
        if (cell == null) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: cell == null");
        }
        if (group == null) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: group == null");
        }
        if (cell.isContainedIn(group)) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: cell is already associated with group");
        }
        if (group.contains(cell)) {
            throw new IllegalArgumentException(KGrid.class.getSimpleName()
                    + ".associate.pre failed: group already contains cell");
        }
        group.add(cell);
        cell.add(group);
    }

    /**
     * Checks whether this grid is full (no more empty cells).
     *
     * @return whether this grid is full
     */
    public boolean isFull() {
        return this.getStateCount(KCell.EMPTY) == 0;
    }

    /**
     * Checks whether this grid is valid.
     *
     * @return whether this is valid
     */
    @Override
    public boolean isValid() {
        for (KEntry entry : entries) {
            if (!entry.isValid()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears the non-blocked cells.
     *
     * @post {@code \forall Cell cell : cells;
     *   (cell.isEmpty()) == ! cell.isBlocked)}
     */
    public void clear() {
        for (final KCell cell : this) {
            if (!cell.isBlocked()) {
                cell.setState(KCell.EMPTY);
            }
        }
    }

    /**
     * Returns entries as a string.
     *
     * @return string representation of the entries
     */
    public String entriesAsString() {
        StringBuilder result = new StringBuilder();
        for (KEntry entry : entries) {
            result.append(entry).append("\n");
        }
        return result.toString();
    }

    /**
     * Converts the grid of cell states to a string in 2D layout.
     *
     * @return string representation of {@code this.matrix}
     */
    public String gridAsString() {
        final StringBuilder result = new StringBuilder();
        for (List<KCell> row : matrix) {
            for (KCell cell : row) {
                result.append(" ");
                result.append(cell.toString());
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Converts this grid to a string.
     *
     * @return string representation of this
     */
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(this.entriesAsString());
        final StringBuilder separator = new StringBuilder("=\n");
        for (List<KCell> row : matrix) {
            for (KCell cell : row) {
                if (!cell.isBlocked() && !cell.isEmpty()) {
                    result.append(separator);
                    separator.setLength(0);
                    result.append(cell.getLocation());
                    result.append(" = ");
                    result.append(cell);
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

}
