package ypa.model;

import java.util.*;

/**
 * A group of cells, without validity condition.
 *
 */
public class YGroup {
    public int groupNum;
    List<YCell> cellGroup;
    private int expectedSum;

    /** Constructs a group of cell. */
    public YGroup(int groupNum) {
        cellGroup = new ArrayList<>(4);
        this.groupNum = groupNum;
    
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
    public boolean equalsExpectedSum() {
        if (isFull()) {
            int currSum = 0;
            for (YCell cell: cellGroup) {
                currSum += cell.getState();
            }
            return currSum == expectedSum;
        } else {
            return false;
        }

    } 
    
    /** Get this list of the loations of the cells whose sum do not equal the target. */
    public List<YCell> getCells() {
        List<YCell> list = new ArrayList<>();
        for (YCell cell: cellGroup) {
            list.add(cell);
        }
        return list;
    }
    
    /** Checks if all 4 cells has been filled out. */
    public boolean isFull() {
        int filledCell = 0;
        for (YCell cell: cellGroup) {
            if (cell.getState() != YCell.EMPTY) {
                filledCell++;
            }
        }
        return filledCell == 4;
    }

    @Override
    public String toString() {
        String res = "ID:" + String.valueOf(groupNum) + ", Expected Sum: " 
            + String.valueOf(expectedSum) + "\n";
        for (YCell yc: cellGroup) {
            res += yc.toString() + "\n";
        }
        return res;
    }
}
