package ci4821.subsystemsimulator.classes;

public class PhysicalMemoryEntry {

    private int frameOwnerPID;

    PhysicalMemoryEntry(){
        frameOwnerPID   = -1;
    }

    public boolean isBeingUsed() {
        return frameOwnerPID != -1;
    }

    public void setFrameOwnerPID(int frameOwnerPID) {
        // TODO: checks
        this.frameOwnerPID = frameOwnerPID;
    }

    public int getIdFrameOwnerPID() {
        return this.frameOwnerPID;
    }

}
