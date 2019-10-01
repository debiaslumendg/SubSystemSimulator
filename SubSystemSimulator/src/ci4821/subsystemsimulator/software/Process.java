/**
 *
 * Proceso a emular representado como un hilo para su ejecución.
 *
 *  Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import java.util.ArrayList;

public class Process extends Thread{

    private MemoryManagerUnit memoryManagerUnit;
    private PageTable pageTable;
    private ArrayList<Integer>   stringRef;
    private String name;
    private int nTextPages, nDataPages;

    /**
     * Crea el proceso
     * @param name      Nombre del proceso
     * @param rutaRefs  Ruta al archivo que contiene las referencias a acceder en memoria
     */
    public Process(MemoryManagerUnit memoryManagerUnit, String name,
    String rutaRefs, int nTextPages, int nDataPages){

        this.name               = name;
        this.nTextPages         = nTextPages;
        this.nDataPages         = nDataPages;
        this.memoryManagerUnit  = memoryManagerUnit;
        this.pageTable          = new PageTable();

        //TODO: Leer rutaRefs
        stringRef = new ArrayList<>(30);
    }


    @Override
    public void run() {
        
        int i       = 5;
        int frameID = -1;

        for (int virtualPageID:stringRef) {

            //TODO: tratar errores de page fault y segmentation fault
            frameID = pageTable.getFrameID(virtualPageID);

            // Acceso a la memoria
            memoryManagerUnit.accessAddress(frameID);

            if(i-- == 1){
                i = 5;
                // TODO: Dormir tiempo aleatorio para hacerlo más real?
                //Thread.sleep();
            }
        }
    }
}
