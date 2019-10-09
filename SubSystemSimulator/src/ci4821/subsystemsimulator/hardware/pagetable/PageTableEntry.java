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


public class PageTableEntry {

    private int pageID, frameID;
    private boolean modified, referenced, valid;

	// Constructor
    public PageTableEntry(){
        pageID = -1;
        frameID = -1;
		modified = false;
		referenced = false;
        valid = false;
    }

	public int getVirtualPageID() {
		return pageID;
	}

	public void setVirtualPageID(int virtualPageID) {
		this.pageID = virtualPageID;
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

	public void setReferenced(boolean referenced) {
		this.referenced  = referenced;
	}
}