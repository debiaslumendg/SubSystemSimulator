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
	 * Guarda uno de los números de página del string de referencia del proceso.
	 */
	private int processPageID;
	/**
	 * Dirección del frame asociado en la memoria principal.
	 */
	private int frameID;
	/**
	 * Indica si la página que tiene asociada fué modificada.
	 */
	private boolean modified;
	/**
	 * Indica si el algoritmo de reemplazo la refenció.
	 */
	private boolean referenced;
	/**
	 * 
	 */
	private boolean valid;

    public PageTableEntry(){
        processPageID = -1;
        frameID = -1;
		modified = false;
		referenced = false;
        valid = false;
    }

	public int getProcessPageID() {
		return processPageID;
	}

	public void setProcessPageID(int processPageID) {
		this.processPageID = processPageID;
	}

	public int getFrameID() {
		return frameID;
	}

	public void setFrameID(int frameID) {
		this.frameID = frameID;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced  = referenced;
	}
}