/**
 * 
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.software.SymProcess;

public class MemoryManagerUnit {

    // SIZE IN KB
    public static final int SIZE = 32;
    public static final int PAGE_SIZE = 4;
    public static final int N_FRAMES = SIZE / PAGE_SIZE;
    private MemoryEntry[] mainMemory;

    public MemoryManagerUnit() {

    	mainMemory = new MemoryEntry[N_FRAMES];

    	for(int i = 0; i < N_FRAMES;i++){
    	    mainMemory[i] = new MemoryEntry();
        }
    }

    /**
     * Utilizamos un método monitor para que solo 1 proceso acceda a la memoria.
     * @param pageID ID de la página accedida
     * @throws PageFaultException
     * @return
     */
    synchronized public int readAddress(int pageID, SymProcess p) throws PageFaultException {
        int realMemoryAddress = translateAddress(pageID, p.getPageTable());
        if (realMemoryAddress != -1 &&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() != Thread.currentThread().getId()) {

            p.getPageTable().getPage(pageID).setReferenced(true);
            return mainMemory[realMemoryAddress].getValue();

        } else {
            throw new PageFaultException();
        }
    }
    
    synchronized public void writeAddress(int pageID, int value, SymProcess p) throws PageFaultException {

        int realMemoryAddress = translateAddress(pageID, p.getPageTable());
    	if (realMemoryAddress != -1) {
    	    p.getPageTable().getPage(pageID).setModified(true);
            mainMemory[realMemoryAddress].setFrameOwnerPID(Thread.currentThread().getId());
            mainMemory[realMemoryAddress].setValue(value);
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
