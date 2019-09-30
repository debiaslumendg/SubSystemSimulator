/**
 *
 * Clase Proceso que crea un hilo para su ejecución.
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

    /**Cadena de enteros con los ID de las páginas virtuales a acceder*/
    private ArrayList<Integer>   referencias; // [12 3 1 4 6 2 34 ]
    private String name;

    /**
     * Crea el proceso
     * @param name Nombre del proceso
     * @param rutaRefs Ruta al archivo que contiene las referencias a acceder en memoria
     */
    public Process(MemoryManagerUnit memoryManagerUnit, String name, String rutaRefs){
        this.name   = name;
        this.memoryManagerUnit = memoryManagerUnit;
        this.pageTable  = new PageTable();

        //TODO: Leer rutaRefs
        referencias = new ArrayList<>(30);
    }


    @Override
    public void run() {
        int i   = 5;
        int frameID = -1;

        for (int virtualPageID:referencias) {

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
