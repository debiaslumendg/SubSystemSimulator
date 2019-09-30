package ci4821.subsystemsimulator.classes;

public class OperatingSystem {

    private PhysicalMemoryUnit physicalMemoryUnit;

    public OperatingSystem(){
        this.physicalMemoryUnit = new PhysicalMemoryUnit();
    }

    /**
     * Comienza un proceso en el simulador.
     * @param nameProcess   Nombre del proceso
     * @param nTEXTPages    Número de páginas de texto
     * @param nDATAPages    Número de páginas de datos
     * @param pathRefs      ruta al archivo que contiene el string de referencias
     */
    public void startProcess(String nameProcess, int nTEXTPages,int nDATAPages,String pathRefs){
        Process process = new Process(physicalMemoryUnit,nameProcess,pathRefs);
        // TODO: Falta utilizar nTEXTPages y nDATAPages para establecer las entradas en la tabla de páginas del proceso
        // TODO: Ejemplo nTEXTPages = 1 process.pageTable[0].setVirtualPageID(0)
        process.start();
    }
}
