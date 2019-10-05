/**
 * Representa el simulador
 */
package ci4821.subsystemsimulator.software;

import java.util.HashMap;
import java.util.Map;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;

public class OperatingSystem {

    private MemoryManagerUnit memoryManagerUnit;
    private Map<Long, SymProcess> processes;

    /**Tabla del disco para los procesos
     * Se guarda para cada proceso.
     * Nos dice cuales páginas están guardadas en disco.
     * */
    private Map<Long, SwapTable> swapTable;


    public OperatingSystem(){
        this.memoryManagerUnit = new MemoryManagerUnit();
        this.processes = new HashMap<Long, SymProcess>();
        this.swapTable = new HashMap<>();
    }

    /**
     * Comienza un proceso en el simulador.
     * @param name          Nombre del proceso
     * @param nTextPages    Número de páginas de texto
     * @param nDataPages    Número de páginas de datos
     * @param pathRefs      ruta al archivo que contiene el string de referencias
     */
    public void startProcess(String name, int nTextPages,int nDataPages,String pathRefs){

        if(nTextPages + nDataPages > PageTable.VIRTUAL_PAGES){
            System.out.println("El número de páginas de texto y datos tienen que ser menor que " + PageTable.VIRTUAL_PAGES);
            System.exit(1);
        }

        SymProcess procesoRunnable = new SymProcess(this,memoryManagerUnit, name, pathRefs, nTextPages, nDataPages);

        Thread process = new Thread(procesoRunnable,name);

        Long newPid = process.getId();

        SwapTable newSwapTable = new SwapTable();

        // Decimos que las páginas de abajo hacia arriba son las de texto
        for(int i = 0; i < nTextPages ; i++){
            newSwapTable.set(i,0);
        }

        // Decimos que las páginas de arriba hacia abajo son las de datos
        for(int i = PageTable.VIRTUAL_PAGES - 1; i >= nTextPages ; i--){
            newSwapTable.set(i,0);
        }

        swapTable.put(newPid,newSwapTable);

        processes.put(newPid, procesoRunnable);

        process.start();
    }

    public void killProcess() {}

    /**
     * Algoritmo de reemplazo de 'Reloj mejorado'
     * @param bitRef        Indica si la página está en memoria o no.
     * @param bitModified   Indica si la página de datos ha sido modificada o no.
     */
    public void superClock(int bitRef, int bitModified) {}
    
    public void handlePageFault(int pageID, SymProcess p) {}
}
