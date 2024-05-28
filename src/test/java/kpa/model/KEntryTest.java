package kpa.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code KEntry}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class KEntryTest {

    /**
     * Test constructor, getDirection, and toString.
     */
    @Test
    public void testConstructor() {
        System.out.println("KEntry");
        String expResult = "a 2 -  9 3";
        Scanner scanner = new Scanner(expResult);
        KEntry instance = new KEntry(scanner);
        assertAll(
                () -> assertEquals(Direction.HORIZONTAL, instance.getDirection(), "direction"),
                () -> assertEquals(expResult, instance.toString(), "toString")
        );
    }

    /**
     * Tests scanEntries.
     */
    @Test
    public void testScanEntries() {
        System.out.println("scanEntries, plain");
        String entry0 = "a 2 -  9 3";
        String entry1 = "b 1 | 17 2";
        String expResult = entry0 + "\n" + entry1 + "\n";
        List<KEntry> result = KEntry.scanEntries(new Scanner(expResult));
        assertAll(
                () -> assertEquals(2, result.size(), "size"),
                () -> assertEquals(entry0, result.get(0).toString(), "get(0)"),
                () -> assertEquals(entry1, result.get(1).toString(), "get(1)")
        );
    }

    /**
     * Tests scanEntries.
     */
    @Test
    public void testScanEntries2() {
        System.out.println("scanEntries, with extra line");
        String entry0 = "a 2 -  9 3";
        String entry1 = "b 1 | 17 2";
        String expResult = entry0 + "\n" + entry1 + "\n=\n";
        Scanner scanner = new Scanner(expResult);
        List<KEntry> result = KEntry.scanEntries(scanner);
        assertAll(
                () -> assertEquals(2, result.size(), "size"),
                () -> assertEquals(entry0, result.get(0).toString(), "get(0)"),
                () -> assertEquals(entry1, result.get(1).toString(), "get(1)"),
                () -> assertTrue(scanner.hasNext("="), "next")
        );
    }

    /**
     * Test isValid().
     */
    @Test
    public void testIsValidAllEmpty() {
        System.out.println("isValid, empty cells");
        String entry = "a 2 -  9 3";
        Scanner scanner = new Scanner(entry);
        KEntry instance = new KEntry(scanner);
        KCell[] cells = new KCell[] {
            new KCell(KCell.EMPTY),
            new KCell(KCell.EMPTY),
            new KCell(KCell.EMPTY)
        };
        for (KCell cell : cells) {
            instance.add(cell);
            cell.add(instance);
        }
        assertTrue(instance.isValid(), "isValid, all 3 empty");
        cells[0].setState(8);
        assertFalse(instance.isValid(), "isValid, 2 empty, too high");
        cells[0].setState(1);
        assertTrue(instance.isValid(), "isValid, 2 empty, not too high");
        cells[1].setState(2);
        assertTrue(instance.isValid(), "isValid, 1 empty, not too high");
        cells[2].setState(6);
        assertTrue(instance.isValid(), "isValid, no empty, sum OK");
        cells[2].setState(7);
        assertFalse(instance.isValid(), "isValid, no empty, sum too high");
        cells[2].setState(5);
        assertFalse(instance.isValid(), "isValid, no empty, sum too low");
    }

}
