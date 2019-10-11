/**
 * Clase que representa un frame de la memoria principal.
 */

package ci4821.subsystemsimulator.hardware;

import java.time.Instant;

public class PageFrame {

    private long frameOwnerPID = -1; // PID del proceso/hilo asignado al frame.
    private int page = -1; // Número de página asignado al frame de la memoria.
    private boolean rBit = false;
    private boolean mBit = false;
    private Instant lastReferencedOn;
    
    public void assign(long frameOwnerPID, int page) {
    	this.frameOwnerPID = frameOwnerPID;
    	this.page = page;
    	reference();
    }
    
    public void evict() {
    	this.frameOwnerPID = -1;
    	this.page = -1;
    	this.rBit = false;
    	this.mBit = false;
    	this.lastReferencedOn = Instant.now();
    }
    
    public void reference() {
    	this.rBit = true;
    	if ((int)(Math.random() * 100) < 20) {
    		this.mBit = true;
    	}
    	this.lastReferencedOn = Instant.now();
    }
    
    public Instant getLastReferencedOn() {
		return lastReferencedOn;
	}

    public long getFrameOwnerPID() {
        return this.frameOwnerPID;
    }
    
    public int getPage() {
        return page;
    }

	public boolean getRBit() {
		return rBit;
	}

	public void setRBit(boolean rBit) {
		this.rBit = rBit;
	}

	public boolean getMBit() {
		return mBit;
	}

	public void setMBit(boolean mBit) {
		this.mBit = mBit;
	}

	

	
}