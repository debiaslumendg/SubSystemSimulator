/**
 * Clase que representa la memoria principal
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.software.SymProcess;
import ci4821.subsystemsimulator.hardware.pagetable.PageTableEntry;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;

import java.util.ArrayList;
import java.util.Random;

public class MemoryManagerUnit {

    /**
     * Tamaño de página en KB.
     */
    public static final int PAGE_SIZE = 4;
    /**
     * Tamaño de la memoria a simular en KB.
     */
    public int size;
    /**
     * Cantidad total de los frames de la memoria principal.
     */
    public int totalFrames;
    /**
     * Representacion de los frames de la memoria principal.
     */
    private ArrayList<MemoryEntry> mainMemory;
    /**
     * Cantidad de page faults ocurridos
     */
    private int pageFaults;

    public MemoryManagerUnit(int size) {

        pageFaults = 0;
        totalFrames = size / PAGE_SIZE;
        mainMemory = new ArrayList<>(totalFrames);
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
        if(pageFrame == -1) {

            // Busca un hueco en la memoria principal
            for( MemoryEntry frame : mainMemory ) {

                if (!frame.isBeingUsed()) {

                    frame.setFrameOwnerPID(p.getPID());
                    frame.setPageID(processPage);
                    pageFrame = mainMemory.indexOf(frame);
                    p.getPageTable().setFrameToPage(processPage, pageFrame);
                }
            }

            // Memoria llena, page fault
            clockAlgorithm(processPage, p);
            throw new PageFaultException();
        }
    }

    /**
     * Le quita la memoria a la página del proceso cuado el proceso ya terminó de
     * ejecutarse o se mata
     * 
     * @param processPage   Página a ser removida
     * @param pageFrame     Frame en dónde se encuentra la página
     * @param p
     */
    synchronized public void removePageFromMemory(int processPage, int pageFrame, SymProcess p) {

        MemoryEntry frame = mainMemory.get(pageFrame);
        frame.setFrameOwnerPID(-1);
        frame.setPageID(-1);
        p.getPageTable().setFrameToPage(processPage, -1);
    }

    synchronized public void clockAlgorithm(int processPage, SymProcess p) {

        pageFaults++;
        PageTable pt = p.getPageTable();
        
        // Busca una página que tenga el bit de referencia en 0
        for( MemoryEntry frame : mainMemory ) {

            PageTableEntry pte = pt.getPageEntry(frame.getPageID());

            if( !pte.isReferenced() ){
                
                frame.setFrameOwnerPID(p.getPID());
                frame.setPageID(processPage);
                int pageFrame = mainMemory.indexOf(frame);
                pt.setFrameToPage(processPage, pageFrame);
                pte.setReferenced(true);
            }
            else {
                pte.setReferenced(false);
            }
        }

        // Si todas las páginas tienen el bit de referencia en 1,
        // colocar en 0 el de una página en aleatorio y llamar nuevamente el algoritmo
        pt.getPageEntry(0).setReferenced(false);
        clockAlgorithm(processPage, p);
    }
}
