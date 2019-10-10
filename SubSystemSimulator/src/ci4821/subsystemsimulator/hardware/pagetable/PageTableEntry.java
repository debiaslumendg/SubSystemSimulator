/**
 * Contiene la clase para una entrada en la Tabla de Página de un proceso.
 *
 *  Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Pérez       10-10574
 */

package ci4821.subsystemsimulator.hardware.pagetable;


public class PageTableEntry {

	/**
	 * Dirección del frame asociado en la memoria principal.
	 */
	private int frameID;
	/**
	 * Indica si el algoritmo de reemplazo la refenció.
	 */
	private boolean referenced;

    public PageTableEntry(){
        frameID = -1;
		referenced = false;
    }

	public int getFrameID() {
		return frameID;
	}

	public void setFrameID(int frameID) {
		this.frameID = frameID;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced  = referenced;
	}
}