package ci4821.subsystemsimulator.software;

public class SwapTableEntry{

    private static final int NULL_VALUE = Integer.MAX_VALUE;
    private boolean isInDisk = false;

    private int valueInDisk  = NULL_VALUE;

    public void setValueInDisk(int newValue) {
        valueInDisk = newValue;
        isInDisk    = true;
    }

    public boolean isValueInDisk() {
        return isInDisk;
    }

    public int getValueInDisk() {
        return valueInDisk;
    }
}
