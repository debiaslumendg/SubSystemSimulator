package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;

public class OperatingSystem {

    private MemoryManagerUnit memoryManagerUnit;

    public OperatingSystem(){
        this.memoryManagerUnit = new MemoryManagerUnit();
    }

    /**
     * Comienza un proceso en el simulador.
     * @param nameProcess   Nombre del proceso
     * @param nTEXTPages    Número de páginas de texto
     * @param nDATAPages    Número de páginas de datos
     * @param pathRefs      ruta al archivo que contiene el string de referencias
     */
    public void startProcess(String nameProcess, int nTEXTPages,int nDATAPages,String pathRefs){
        Process process = new Process(memoryManagerUnit,nameProcess,pathRefs);
        // TODO: Falta utilizar nTEXTPages y nDATAPages para establecer las entradas en la tabla de páginas del proceso
        // TODO: Ejemplo nTEXTPages = 1 process.pageTable[0].setVirtualPageID(0)
        process.start();
    }
}
