/**
 * Clase que representa la memoria principal
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.software.SymProcess;
import ci4821.subsystemsimulator.util.ConsoleLogger;

public class MemoryManagerUnit {

    /**
     * Tamaño de la memoria a simular en KB.
     */
    public static final int SIZE = 32;
    /**
     * Tamaño de página en KB.
     */
    public static final int PAGE_SIZE = 4;
    /**
     * Cantidad total de los frames de la memoria principal.
     */
    public static final int TOTAL_FRAMES = SIZE / PAGE_SIZE;
    /**
     * Representacion de los frames de la memoria principal.
     */
    private MemoryEntry[] mainMemory;
    private ConsoleLogger logger;

    public MemoryManagerUnit() {

    	mainMemory = new MemoryEntry[TOTAL_FRAMES];
        logger = ConsoleLogger.getInstance();

        // Inicialización de los frames de la memoria
    	for(int i = 0; i < TOTAL_FRAMES;i++){
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


        logger.logMessage(ConsoleLogger.Level.READ_PAGE,"Leyendo [ Página: " + pageID + "] -> [Frame : " + realMemoryAddress + "]");

        if(realMemoryAddress != -1 &&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() != Thread.currentThread().getId()){
            logger.logMessage(ConsoleLogger.Level.READ_PAGE,
                    "Error , no puedes leer ese frame. [Frame : " + realMemoryAddress + "] es de [ Proceso : " +
                            mainMemory[realMemoryAddress].getIdFrameOwnerPID() +"]");
        }

        if (realMemoryAddress != -1 &&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() == Thread.currentThread().getId()) {

            logger.logMessage(ConsoleLogger.Level.READ_PAGE,
                    "[Frame : " + realMemoryAddress + "] -> Valor : " + mainMemory[realMemoryAddress].getData());

            p.getPageTable().getPage(pageID).setReferenced(true);
            return mainMemory[realMemoryAddress].getData();

        } else {
            throw new PageFaultException();
        }
    }
    
    synchronized public void writeAddress(int pageID, int value, SymProcess p) throws PageFaultException {
        //TODO: Chequear si no hay page faulteros para darles prioridad
        int realMemoryAddress = translateAddress(pageID, p.getPageTable());

        logger.logMessage(ConsoleLogger.Level.WRITE_PAGE,
                "Escribiendo a [ Página: " + pageID + "] -> [Frame : " + realMemoryAddress + "] Valor: " +
                        value);

        if(realMemoryAddress != -1 &&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() != Thread.currentThread().getId()){
            logger.logMessage(ConsoleLogger.Level.WRITE_PAGE,
                    "Error , no puedes escribir ahí. [Frame : " + realMemoryAddress + "] es de [ Proceso : " +
                            mainMemory[realMemoryAddress].getIdFrameOwnerPID() +"]");
        }

    	if (realMemoryAddress != -1&&
                mainMemory[realMemoryAddress].getIdFrameOwnerPID() == Thread.currentThread().getId()) {
            
            p.getPageTable().getPage(pageID).setModified(true);
            mainMemory[realMemoryAddress].setData(value);
            
            logger.logMessage(ConsoleLogger.Level.WRITE_PAGE,
                    "Escribió en [ Frame: " + realMemoryAddress + "] Valor: " +
                            mainMemory[realMemoryAddress].getData());
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
        // TODO: Asignarle memoria si hay
        // Ejecutar algoritmo de reemplazo de ser necesario
        System.out.println("Pafe Fault Handler para Proceso: " + p.getPID() + " virtualPageID : " + virtualPageID);

        for(int i = 0; i < TOTAL_FRAMES;i++){
            if (!mainMemory[i].isBeingUsed()){

                System.out.println("Frame " + i + " está libre, asignado al proceso: " + p.getPID() );
                mainMemory[i].setFrameOwnerPID(p.getPID());

                // SWAP valor en disco a memoria
                mainMemory[i].setData(entry.getValueInDisk());

                p.getPageTable().setFrameID(virtualPageID,i);
                return;
            }
        }

        // No encontró ningún frame libre, entonces llama al algoritmo de reemplazo
    }

}
