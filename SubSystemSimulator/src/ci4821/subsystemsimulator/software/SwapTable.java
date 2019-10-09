package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;

public class SwapTable {

    SwapTableEntry[] tableEntries = new SwapTableEntry[PageTable.PAGES];

    SwapTable(){

        for(int i = 0; i < PageTable.PAGES;i++){
            tableEntries[i] = new SwapTableEntry();
        }
    }

    public void set(int pos, int newValue) {
        tableEntries[pos].setValueInDisk(newValue);
    }

    public SwapTableEntry getEntry(int pageID) {
        return tableEntries[pageID];
    }
}
