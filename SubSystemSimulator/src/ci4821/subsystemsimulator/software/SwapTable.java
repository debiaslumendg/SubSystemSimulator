package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;

class SwapTableEntry{

    private static final int NULL_VALUE = Integer.MAX_VALUE;
    private boolean isInDisk = false;

    private int valueInDisk  = NULL_VALUE;

    public void setValueInDisk(int newValue) {
        valueInDisk = newValue;
        isInDisk    = true;
    }
}

public class SwapTable {

    SwapTableEntry[] tableEntries = new SwapTableEntry[PageTable.VIRTUAL_PAGES];

    SwapTable(){

        for(int i = 0; i < PageTable.VIRTUAL_PAGES;i++){
            tableEntries[i] = new SwapTableEntry();
        }
    }

    public void set(int pos, int newValue) {
        tableEntries[pos].setValueInDisk(newValue);
    }
}
