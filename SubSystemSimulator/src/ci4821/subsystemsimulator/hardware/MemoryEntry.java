/**
 * 
 */

package ci4821.subsystemsimulator.hardware;

public class MemoryEntry {

    private long frameOwnerPID;
    private int value;


    MemoryEntry() {
        frameOwnerPID = -1;
    }

    public boolean isBeingUsed() {
        return frameOwnerPID != -1;
    }

    public void setFrameOwnerPID(long frameOwnerPID) {
        this.frameOwnerPID = frameOwnerPID;
    }


    public long getIdFrameOwnerPID() {
        return this.frameOwnerPID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}