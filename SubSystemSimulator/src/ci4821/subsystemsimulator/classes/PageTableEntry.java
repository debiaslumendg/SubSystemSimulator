/**
 *
 * Contiene la clase para una entrada en la Página de Tabla de un proceso.
 *
 *  Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.classes;


class PageTableEntry {

    private int virtualPageID, frameID;

    // TODO: usar... dice si esta página es una de datos o de texto
    private boolean isDATAPage;

    PageTableEntry(){
        virtualPageID = -1;
        frameID = -1;
    }

    public boolean isBeingUsed() {
        return virtualPageID != -1;
    }
    public int getVirtualPageID() {
        return virtualPageID;
    }

    public void setVirtualPageID(int virtualPageID) {
        // TODO: checks
        this.virtualPageID = virtualPageID;
    }

    public int getFrameID() {
        return frameID;
    }

    public void setFrameID(int frameID) {
        // TODO: checks
        this.frameID = frameID;
    }
}