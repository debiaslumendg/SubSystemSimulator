/**
 * 
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.software.SwapTableEntry;
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
        //TODO: Chequear si no hay page faulteros para darles prioridad
        int realMemoryAddress = translateAddress(pageID, p.getPageTable());

        if (realMemoryAddress != -1 &&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() == Thread.currentThread().getId()) {

            System.out.println("Proceso: " + p.getPID() + " leyó memoria dir : " +
                    realMemoryAddress + " valor: " + mainMemory[realMemoryAddress].getValue());
            p.getPageTable().getPage(pageID).setReferenced(true);
            return mainMemory[realMemoryAddress].getValue();

        } else {
            throw new PageFaultException();
        }
    }
    
    synchronized public void writeAddress(int pageID, int value, SymProcess p) throws PageFaultException {
        //TODO: Chequear si no hay page faulteros para darles prioridad
        int realMemoryAddress = translateAddress(pageID, p.getPageTable());
    	if (realMemoryAddress != -1&&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() == Thread.currentThread().getId()) {
    	    p.getPageTable().getPage(pageID).setModified(true);
            mainMemory[realMemoryAddress].setValue(value);
            System.out.println("Proceso: " + p.getPID() + " escribió en memoria dir : " +
                    realMemoryAddress + " valor: " + mainMemory[realMemoryAddress].getValue());
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
     * @param p
     * @param virtualPageID
     */

    synchronized public void pageFaultHandler(SymProcess p, SwapTableEntry entry, int virtualPageID) {
        // TODO: falta
        // TODO: Asignarle memoria si hay
        // TODO: Ejecutar algoritmo de reemplazo de ser necesario
        System.out.println("Pafe Fault Handler para Proceso: " + p.getPID() + " virtualPageID : " + virtualPageID);

        for(int i = 0; i < N_FRAMES;i++){
            if (!mainMemory[i].isBeingUsed()){

                System.out.println("Frame " + i + " está libre, asignado al proceso: " + p.getPID() );
                mainMemory[i].setFrameOwnerPID(p.getPID());

                // SWAP valor en disco a memoria
                mainMemory[i].setValue(entry.getValueInDisk());

                p.getPageTable().setFrameID(virtualPageID,i);
                return;
            }
        }
    }

}
