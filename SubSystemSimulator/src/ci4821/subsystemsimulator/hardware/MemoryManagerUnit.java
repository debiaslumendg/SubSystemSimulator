/**
 * Clase que representa la memoria principal
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.software.ClockAlgorithm;
import ci4821.subsystemsimulator.software.SymProcess;
import ci4821.subsystemsimulator.util.ConsoleLogger;

import java.util.ArrayList;

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
    private ArrayList<MemoryEntry> mainMemory;
    private ClockAlgorithm clockAlgorithm;

    public MemoryManagerUnit() {
        mainMemory = new ArrayList<>(TOTAL_FRAMES);
        clockAlgorithm = new ClockAlgorithm();
    }

    /**
     * Le asigna memoria a la página del proceso sino tiene, sino la referencia.
     * 
     * @param processPage   Página a ser asignada
     * @param p             Proceso
     * @throws PageFaultException
     */
    synchronized public void setPageInMemory(int processPage, SymProcess p) throws PageFaultException {

        int pageFrame = p.getPageTable().getFrameID(processPage);
        
        // Si la página no está en memoria, asignarle y vericar que el proceso sea el actual
        if(pageFrame != -1) {

            if(mainMemory.get(pageFrame).getFrameOwnerPID() == Thread.currentThread().getId()) {

                MemoryEntry memoryEntry = new MemoryEntry();
                memoryEntry.setFrameOwnerPID(p.getPID());
                memoryEntry.setPage(processPage);
                
                // Si se le asigna memoria a la página del proceso, se actualiza su tabla de página
                if(mainMemory.add(memoryEntry)) {
        
                    int frame = mainMemory.indexOf(memoryEntry);
                    p.getPageTable().setFrameToPage(processPage, frame);
                }
                else { // Sino page fault y llama al algoritmo de reemplazo
                    
                    throw new PageFaultException();
                    clockAlgorithm.start();
                }
            }
            else {
                
            }
        }
        else { // Sino actualizar su bit referencia
            p.getPageTable().getPage(processPage).setReferenced(true);
        }
    }
}
