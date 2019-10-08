/**
 * 
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.software.SymProcess;

public class MemoryManagerUnit {
    
    public static final int SIZE = 32;
    public static final int PAGE_SIZE = 4;
    private MemoryEntry[] mainMemory;

    public MemoryManagerUnit() {
    	mainMemory = new MemoryEntry[SIZE / PAGE_SIZE];
    }

    /**
     * Utilizamos un método monitor para que solo 1 proceso acceda a la memoria.
     * @param frameID ID del frame accedido
     * @throws PageFaultException 
     */
    synchronized public void readAddress(int pageID, SymProcess p) throws PageFaultException {
    	if (translateAddress(pageID, p.getPageTable()) == -1) {
    		throw new PageFaultException();
    	}
    }
    
    synchronized public void writeAddress(int pageID, SymProcess p) throws PageFaultException {
    	if (translateAddress(pageID, p.getPageTable()) != -1) {
    		p.getPageTable().getPage(pageID).setModified(true);
    	} else {
    		throw new PageFaultException();
    	}
    }

    private int translateAddress(int pageID, PageTable pt) {
    	return pt.getFrameID(pageID);
    }
    
    
    
    /**
     * Este pageFaultHandler no lo veo correcto estando miembro de PhysicalMemoryUnit?
     * Pero si consideramos que se tienen que sincronizar los procesos para que
     * dos procesos no accedan a este método y que si un método está aquí no tiene que estar en
     * accessAddress ... TODO: falta pensarlo más aquí
     * @param processID
     * @param frameID
     */

    synchronized public void pageFaultHandler(int processID, int frameID) {
        // TODO: falta
    }

}
