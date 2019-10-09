/**
 * Representa el simulador
 */
package ci4821.subsystemsimulator.software;

import java.util.HashMap;
import java.util.Map;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.util.ConsoleLogger;

public class OperatingSystem {

    private final ConsoleLogger logger;
    private MemoryManagerUnit memoryManagerUnit;
    private Map<Long, Thread> processes;

    /**Tabla del disco para los procesos
     * Se guarda para cada proceso.
     * Nos dice cuales páginas están guardadas en disco. y su contenido
     * */
    private Map<Long, SwapTable> swapTable;


    public OperatingSystem(){
        this.memoryManagerUnit = new MemoryManagerUnit();
        this.processes = new HashMap<Long, Thread>();
        this.swapTable = new HashMap<>();
        logger = ConsoleLogger.getInstance();
        logger.logMessage(ConsoleLogger.Level.INFO,"Creadas estructuras para la emulación");
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
            logger.logMessage( ConsoleLogger.Level.ERROR,
                    "No se pudo crear proceso " + name +
                            " el número de páginas de texto y datos tienen que ser menor que " +
                            PageTable.VIRTUAL_PAGES);
            return;
        }

        SymProcess procesoRunnable = new SymProcess(this,memoryManagerUnit, name, pathRefs, nTextPages, nDataPages);

        Thread process = new Thread(procesoRunnable,name);

        Long newPid = process.getId(); //TODO: Es esto realmente único? Si yo reinicio este hilo esto cambia?

        logger.logMessage(
                ConsoleLogger.Level.NUEVO_PROCESO,
                String.format(
                "Creado nuevo proceso \"%s\" PID:%s ( TEXTO: %d,DATOS:%d)",
                name,
                newPid,
                nTextPages,
                nDataPages
        ));

        procesoRunnable.setPID(newPid);

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

        processes.put(newPid, process);

        process.start();
    }

    public void killProcess() {}

    /**
     * Algoritmo de reemplazo de 'Reloj mejorado'
     * @param bitRef        Indica si la página está en memoria o no.
     * @param bitModified   Indica si la página de datos ha sido modificada o no.
     */
    public void superClock(int bitRef, int bitModified) {}
    
    public void handlePageFault(int pageID, SymProcess p) {

        logger.logMessage( ConsoleLogger.Level.PAGE_FAULT,
                "[ Proceso: " + p.getPID() + "] intentó acceder a [Página  : " +
                pageID + "] -> [Frame : " + p.getPageTable().getFrameID(pageID) + "]");

        SwapTable swapTable = this.swapTable.get(p.getPID());

        SwapTableEntry tableEntry = swapTable.getEntry(pageID);

        if(tableEntry.isValueInDisk()) {
            memoryManagerUnit.pageFaultHandler(p, tableEntry,pageID);
        }else{
            logger.logMessage( ConsoleLogger.Level.PAGE_FAULT,
                    "[ Proceso: " + p.getPID() + "] intentó acceder a [Página : " + pageID +
                            "] no disponible en el SWAP Disk.");
        }
    }
}
