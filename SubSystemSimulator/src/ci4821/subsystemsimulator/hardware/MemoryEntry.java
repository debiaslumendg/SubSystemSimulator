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
     * Número de página asignado al frame de la memoria.
     */
    private int page;


    MemoryEntry() {
        frameOwnerPID = -1;
        page = -1;
    }

    public boolean isBeingUsed() {
        return frameOwnerPID != -1;
    }

    public void setFrameOwnerPID(long frameOwnerPID) {
        this.frameOwnerPID = frameOwnerPID;
    }

    public long getFrameOwnerPID() {
        return this.frameOwnerPID;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPage() {
        return page;
    }
}