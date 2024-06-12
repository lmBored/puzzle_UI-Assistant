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
    public YGroup() {
        cellGroup = new ArrayList<>();
        currCellCount = 0;
    
    }
    
    public void setExpectedSum(int expectedSum) {
        this.expectedSum = expectedSum;
    }
    
    public void addTypedCell(YCell ycell) {
        
    }

}
