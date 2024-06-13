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

    /* Constructor for the cell group */
    public YGroup() {
        cellGroup = new ArrayList<>(4);
        currCellCount = 0;
    
    }
    
    public void setExpectedSum(int expectedSum) {
        this.expectedSum = expectedSum;
    }
    
    public void addTypedCell(YCell ycell) {
        if (currCellCount < 4) {
            cellGroup.add(ycell);
            currCellCount++;
        }
    }

    public void removeCell(int index) {
        cellGroup.remove(index);
    }

    public boolean checkExpectedSum() {
        int currSum = 0;
        for (YCell cell: cellGroup) {
            currSum += cell.getState();
        }
        return currSum == expectedSum;
    } 

}
