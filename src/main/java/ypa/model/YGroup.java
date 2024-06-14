package ypa.model;

import java.util.*;

/**
 * A group of cells, without validity condition.
 *
 */
public class YGroup {

    List<YCell> cellGroup;
    private int expectedSum;
    private final int cellCount = 4;
    private int currCellCount;

    /** Constructs a group of cell. */
    public YGroup() {
        cellGroup = new ArrayList<>(4);
        currCellCount = 0;
    
    }
    
    public void setExpectedSum(int expectedSum) {
        this.expectedSum = expectedSum;
    }

    /** Add a cell to the current group. */    
    public void addCell(YCell cell) {
        cellGroup.add(cell);
        cell.setGroup(this);
    }
    
    public void removeCell(int index) {
        cellGroup.remove(index);
    }

    /** Check the current sum of cells against the expected sum. */
    public boolean checkExpectedSum() {
        int currSum = 0;
        for (YCell cell: cellGroup) {
            currSum += cell.getState();
        }
        return currSum == expectedSum;
    } 
    
    /** Get this list of the loations of the cells whose sum do not equal the target. */
    public List<Integer> getViolatedCells() {
        List<Integer> list = new ArrayList<>();
        for (YCell cell: cellGroup) {
            list.add(cell.getLocation());
        }
        return list;
    }

}
