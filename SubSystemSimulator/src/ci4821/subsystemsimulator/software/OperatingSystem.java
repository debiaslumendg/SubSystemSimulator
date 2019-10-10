/**
 * Representa el simulador
 *
 * Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Pérez       10-10574
 */
package ci4821.subsystemsimulator.software;

import java.util.HashMap;
import java.util.Map;

import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.util.ConsoleLogger;

public class OperatingSystem {

    private final ConsoleLogger logger;
    private MemoryManagerUnit mmu;
    private Map<Long, Thread> processes;

    public OperatingSystem() {

        this.mmu = new MemoryManagerUnit();
        this.processes = new HashMap<Long, Thread>();
        logger = ConsoleLogger.getInstance();
        logger.logMessage(ConsoleLogger.Level.INFO, "Creadas estructuras para la emulación");
    }

    /**
     * Comienza un proceso en el simulador asignándole memoria a las primeras
     * páginas de su string de referencia.
     * 
     * @param name      Nombre del proceso
     * @param refString String de referencia
     */
    public void startProcess(String name, String refString) {

        SymProcess proceso = new SymProcess(name, refString, this);
        Thread process = new Thread(proceso, name);
        Long newPid = process.getId(); // Es esto realmente único? Si yo reinicio este hilo esto cambia?

        logger.logMessage(ConsoleLogger.Level.NUEVO_PROCESO,
                String.format("Creado nuevo proceso \"%s\" PID:%s", name, newPid));

        proceso.setPID(newPid);
        processes.put(newPid, process);
        process.start();
    }

    public void killProcess() {
    }

    /**
     * Asigna memoria a la página del string de refrencia
     * 
     * @param processPage
     * @param p
     */
    public void asingMemory(int processPage, SymProcess p) {

        try {
            mmu.setPageInMemory(processPage, p);
        } catch (PageFaultException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
