/**
 * Representa el simulador
 *
 * Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Pérez       10-10574
 */
package ci4821.subsystemsimulator.software;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.hardware.PageFrame;
import ci4821.subsystemsimulator.util.ConsoleLogger;
import ci4821.subsystemsimulator.util.LogStats;

public class OperatingSystem {

    private final ConsoleLogger logger;
    private MemoryManagerUnit mmu;
    private Map<Long, Thread> processes;
    private Map<Long, List<Integer>> pageTables;
    private PageReplacementAlgorithm pra;
    private LogStats statsHandler;

    public OperatingSystem(int memorySize) {

        this.mmu = new MemoryManagerUnit(memorySize);
        this.processes = new HashMap<>();
        this.pageTables = new HashMap<>();
        this.pra = new WSClock(mmu);
        logger = ConsoleLogger.getInstance();
        logger.logMessage(ConsoleLogger.Level.INFO, "Creadas estructuras para la emulación");

        statsHandler = LogStats.getInstance();
    }


    public void createProcess(Integer size) {

        SymProcess p = new SymProcess(size, this);
        Thread pThread = new Thread(p);
        p.setPID(pThread.getId());
        processes.put(pThread.getId(), pThread);
        pageTables.put(pThread.getId(), new ArrayList<>(Collections.nCopies(size, -1)));

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

        statsHandler.incrementStat(p.getPID(),LogStats.StatType.REFS_MEM);

        if (pageFrameAddress >= 0) {
            logger.logMessage(ConsoleLogger.Level.ASIG_PAGE,
                "- Actualizada página " + processPage + " al frame " + pageFrameAddress +
                " del proceso " + p.getPID()
            );
    		mmu.getPageFrame(pageFrameAddress).reference();
    	} else {
            statsHandler.incrementStat(p.getPID(),LogStats.StatType.PAGE_FAULT);
            int targetPageFrame;
    		if (mmu.hasFreeFrames()) {
                targetPageFrame = mmu.getLastFreeFrame();
                logger.logMessage(ConsoleLogger.Level.ASIG_PAGE,
                    "- Asignado frame " + targetPageFrame + " a la página " + processPage +
                    " del proceso " + p.getPID()
                );
    		} else {
                statsHandler.incrementStat(p.getPID(),LogStats.StatType.WSCLOCK_);
                targetPageFrame = pra.getReplacementPageFrame();
                logger.logMessage(ConsoleLogger.Level.PAGE_FAULT,
                    "- Nuevo frame " + targetPageFrame + " para la página " + processPage +
                    " del proceso " + p.getPID()
                );
    			evictProcessPage(targetPageFrame);
    		}

            logger.logMessage(ConsoleLogger.Level.MEM_PAGE,
                "- Actualizada página " + processPage + " al frame " + targetPageFrame +
                " del proceso " + p.getPID()
            );
            mmu.assignPageFrame(targetPageFrame, processPage, p);
            pageTable.set(processPage,targetPageFrame);
            mmu.getPageFrame(targetPageFrame).reference();
    	}
    }
        
    public Collection<Thread> getProcesses() {
    	return processes.values();
    }
    
    private void evictProcessPage(int targetPageFrame) {
    	PageFrame pf = mmu.getPageFrame(targetPageFrame);
    	long _pid = pf.getFrameOwnerPID();
        statsHandler.incrementStat(_pid,LogStats.StatType.WSCLOCK_TARGET);
    	int _page = pf.getPage();
    	mmu.freePageFrame(targetPageFrame);
		pageTables.get(_pid).set(_page, -1);
    }
}
