/**
 *
 * Contiene la clase para una entrada en la Página de Tabla de un proceso.
 *
 *  Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Perez       10-10574
 */

package ci4821.subsystemsimulator.hardware.pagetable;


class PageTableEntry {

    private int virtualPageID, frameID, bitPresent, bitModified, bitValid;

    // Constructor
    PageTableEntry(){
        virtualPageID = -1;
        frameID = -1;
        bitPresent = 0;
        bitModified = 0;
        bitValid = 1;
    }

    // Determina si la página está siendo usada o no
    public boolean isBeingUsed() {
        return bitPresent != 0;
    }

    // Obtiene el numero de la pagina
    public int getVirtualPageID() {
        return virtualPageID;
    }

    // Asigna el numero a la pagina
    public void setVirtualPageID(int virtualPageID) {
        // TODO: checks
        this.virtualPageID = virtualPageID;
    }

    // Obtiene el numero de frame de la memoria principal
    public int getFrameID() {
        return frameID;
    }

    // Asigna el frame donde se estara ejecutando la pagina
    public void setFrameID(int frameID) {
        // TODO: checks
        this.frameID = frameID;
    }
}