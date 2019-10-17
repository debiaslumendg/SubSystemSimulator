/**
 * Representa el simulador
 *
 * Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Pérez       10-10574
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.hardware.PageFrame;
import ci4821.subsystemsimulator.util.ConsoleLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class OperatingSystem {

    private final ConsoleLogger logger;
    private MemoryManagerUnit mmu;
    private Map<Long, Thread> processes;
    private Map<Long, List<Integer>> pageTables;
    private PageReplacementAlgorithm pra;
    private Long lastPID = 0L;

    public OperatingSystem(int memorySize) {

        this.mmu = new MemoryManagerUnit(memorySize);
        this.processes = new HashMap<>();
        this.pageTables = new HashMap<>();
        this.pra = new WSClock(mmu);
        logger = ConsoleLogger.getInstance();
        logger.logMessage(ConsoleLogger.Level.INFO, "Creadas estructuras para la emulación");
    }


    public void createProcess(Integer size) {

        SymProcess p = new SymProcess(size, this);
        processes.put(p.getPID(), new Thread(p));
        pageTables.put(p.getPID(), new ArrayList<>(Collections.nCopies(size, -1)));
        
        logger.logMessage(ConsoleLogger.Level.NUEVO_PROCESO,
                String.format("Creado nuevo proceso PID: %d", p.getPID()));
    }
    
    public void startProcesses() {
        for(Long pid : processes.keySet()) {
        	processes.get(pid).start();
        	logger.logMessage(ConsoleLogger.Level.PROCESO_INICIADO,
                    String.format("Iniciado proceso: %d", pid));
        }
    }

    /**
     * Asigna memoria a la página del string de refrencia
     * 
     * @param processPage
     * @param p
     */
    public synchronized void referencePage(int processPage, SymProcess p) {
        List<Integer> pageTable = pageTables.get(p.getPID());
    	Integer pageFrameAddress = pageTable.get(processPage);

        logger.logMessage(ConsoleLogger.Level.INFO,
                "- Cambiada página virtual " + processPage + " a frame " + pageFrameAddress
        );

    	if (pageFrameAddress >= 0) {
    		mmu.getPageFrame(pageFrameAddress).reference();
    	} else {
            int targetPageFrame;
    		if (mmu.hasFreeFrames()) {
                targetPageFrame = mmu.getLastFreeFrame();
                logger.logMessage(ConsoleLogger.Level.INFO,
                        "- Asignado frame " + targetPageFrame + " a la página virtual " + processPage
                );
    		} else {
                targetPageFrame = pra.getReplacementPageFrame();
                logger.logMessage(ConsoleLogger.Level.INFO,
                        "- A reemplazar frame " + targetPageFrame + " para la página virtual " + processPage
                );
    			evictProcessPage(targetPageFrame);
    		}

            mmu.assignPageFrame(targetPageFrame, processPage, p);
            pageTable.set(processPage,targetPageFrame);

            mmu.getPageFrame(targetPageFrame).reference();

    	}
    }
    
    public Long getNewPID() {
    	return lastPID++;
    }
    
    public Collection<Thread> getProcesses() {
    	return processes.values();
    }
    
    private void evictProcessPage(int targetPageFrame) {
    	PageFrame pf = mmu.getPageFrame(targetPageFrame);
    	long _pid = pf.getFrameOwnerPID();
    	int _page = pf.getPage();
    	mmu.freePageFrame(targetPageFrame);
		pageTables.get(_pid).set(_page, -1);
    }
}
