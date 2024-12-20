package ypa.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the YEntry class.
 */
public class YEntryTest {

    /**
     * Test the constructor with expected sum.
     */
    @Test
    public void testConstructorWithExpectedSum() {
        YEntry entry = new YEntry(15);
        assertEquals(15, entry.getExpectedSum());
    }

    /**
     * Test the constructor with scanner.
     */
    @Test
    public void testConstructorWithScanner() {
        Scanner scanner = new Scanner("20");
        YEntry entry = new YEntry(scanner);
        assertEquals(20, entry.getExpectedSum());
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        YEntry entry = new YEntry(25);
        assertEquals("Expected Sum: 25", entry.toString());
    }

    /**
     * Test the toStringLong method.
     */
    @Test
    public void testToStringLong() {
        YEntry entry = new YEntry(30);
        assertEquals("{ expectedSum: 30 }", entry.toStringLong());
    }

    /**
     * Test the scanEntries method.
     */
    @Test
    public void testScanEntries() {
        Scanner scanner = new Scanner("5 10 15 20");
        List<YEntry> entries = YEntry.scanEntries(scanner);
        assertEquals(4, entries.size());
        assertEquals(5, entries.get(0).getExpectedSum());
        assertEquals(10, entries.get(1).getExpectedSum());
        assertEquals(15, entries.get(2).getExpectedSum());
        assertEquals(20, entries.get(3).getExpectedSum());
    }

    /**
     * Test the getSpecification method.
     */
    @Test
    public void testGetSpecification() {
        YEntry entry = new YEntry(35);
        assertEquals(entry, entry.getSpecification());
    }

    /**
     * Test the getLength method.
     */
    @Test
    public void testGetLength() {
        YEntry entry = new YEntry(45);
        assertEquals(4, entry.getLength());
    }
}