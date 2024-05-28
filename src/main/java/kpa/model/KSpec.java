package kpa.model;

import java.util.Scanner;
import java.util.Set;

/**
 * Specification for a Kakuro combination, consisting of a sum and a length
 * (immutable).
 *
 * @inv NonNegative: {@code 0 <= getSum() && 0 <= getLength()}
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class KSpec {

    /** The sum. */
    private final int sum;

    /** The number of numbers. */
    private final int length;

    /**
     * Constructs a new {@code KSpec} with given sum and length.
     *
     * @param sum  the given sum
     * @param length  the given length
     */
    public KSpec(final int sum, final int length) {
        assert 0 <= sum : "KSpec.pre failed: sum " + sum + " < 0";
        assert 0 <= length : "KSpec.pre failed: length " + length + " < 0";
        this.sum = sum;
        this.length = length;
    }

    /**
     * Constructs a new {@code KSpec} from given set of integers.
     *
     * @param combination  the given combination
     */
    public KSpec(final Set<Integer> combination) {
        assert combination != null : "KSpec.pre failed: combination == null";
        int sum = 0;
        for (int d : combination) {
            sum += d;
        }
        this.sum = sum;
        this.length = combination.size();
    }

    /**
     * Constructs a new {@code KSpec} from a given scanner.
     *
     * @param scanner  the given scanner
     */
    public KSpec(final Scanner scanner) {
        this.sum = scanner.nextInt();
        this.length = scanner.nextInt();
    }

    public int getSum() {
        return sum;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return String.format("%2d %1d", sum, length);
    }

    public String toStringLong() {
        return "{ sum: " + sum + ", length: " + length + " }";
    }

}
