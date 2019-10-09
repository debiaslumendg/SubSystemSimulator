/**
 * Clase que representa un frame de la memoria principal.
 */

package ci4821.subsystemsimulator.hardware;

public class MemoryEntry {

    /**
     * ID/PID del proceso/hilo asignado al frame.
     */
    private long frameOwnerPID;
    /**
     * Data contenida dentro del frame.
     */
    private int data;


    MemoryEntry() {
        frameOwnerPID = -1;
        data = -1;
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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}