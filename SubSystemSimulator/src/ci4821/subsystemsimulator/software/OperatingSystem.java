/**
 * Representa el simulador
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;

public class OperatingSystem {

    private MemoryManagerUnit memoryManagerUnit;

    public OperatingSystem(){
        this.memoryManagerUnit = new MemoryManagerUnit();
    }

    /**
     * Comienza un proceso en el simulador.
     * @param name          Nombre del proceso
     * @param nTextPages    Número de páginas de texto
     * @param nDataPages    Número de páginas de datos
     * @param pathRefs      ruta al archivo que contiene el string de referencias
     */
    public void startProcess(String name, int nTextPages,int nDataPages,String pathRefs){
        
        Thread process = new Thread(new SymProcess(memoryManagerUnit,name,pathRefs,nTextPages,nDataPages));
        // TODO: Falta utilizar nTEXTPages y nDATAPages para establecer las entradas en la tabla de páginas del proceso
        // TODO: Ejemplo nTEXTPages = 1 process.pageTable[0].setVirtualPageID(0)
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
