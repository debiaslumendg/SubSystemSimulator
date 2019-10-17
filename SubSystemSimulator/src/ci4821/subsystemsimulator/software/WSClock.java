package ci4821.subsystemsimulator.software;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Queue;

import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.hardware.PageFrame;
import ci4821.subsystemsimulator.util.ConsoleLogger;

public class WSClock implements PageReplacementAlgorithm {

	private final ConsoleLogger logger;
	private MemoryManagerUnit mmu;
	private Queue<PageFrame> diskQueue;
	private int currentPageFrame = 0;
	private int maxWrite = 5;
	private int ageThreshold = 10000;
	
	public WSClock(MemoryManagerUnit mmu) {
		this.mmu = mmu;
		this.diskQueue = new ArrayDeque<>();
		logger = ConsoleLogger.getInstance();
	}
	
	@Override
	public int getReplacementPageFrame() {
		
		logger.logMessage(ConsoleLogger.Level.PAGE_FAULT, "WSClock ejecutándose");
		
		int cycleStart = currentPageFrame;
		boolean cycleComplete = false;
		boolean wroteToDisk = false;
		int cleanPageFrame = -1;
		
		while (true) {
			
			PageFrame pf = mmu.getPageFrame(currentPageFrame);
			
			if (!cycleComplete) { 									// El reloj no ha dado la vuelta completa
				
				if (pf.getRBit()) pf.setRBit(false);				// Se marca la página como no referenciada
				else if (pf.getMBit()) ScheduleWriteToDisk(pf);		// Se agenda una escritura al disco
				else if (this.isDue(pf)) return currentPageFrame;	// Si la página superó su tiempo se vida es seleccionada
				
				if (cleanPageFrame < 0 && !pf.getMBit()) cleanPageFrame = currentPageFrame;
				
			} else { 												// El reloj dio la vuelta completa
				
				if (wroteToDisk) cycleComplete = false; 			// Si se escribieron páginas al disco el ciclo continúa normal
				else { 												// No se escribieron páginas al disco, hay dos casos:
					// Si se encontró una página limpia se selecciona
					if (cleanPageFrame >= 0) return cleanPageFrame;
					else { 	// Si no se encontraron páginas limpias se fuerza la escritura al disco y se selecciona la página
						WritePageToDisk(pf);
						return currentPageFrame;
					}
				}
			}
			
			currentPageFrame = (currentPageFrame + 1) % mmu.getLastFrame();
			
			if (currentPageFrame == cycleStart) {					// Se completó el ciclo y se envían las páginas agendadas al disco
				cycleComplete = true;
				wroteToDisk = WriteQueueToDisk();
			}
		}
	}
	
	private void ScheduleWriteToDisk(PageFrame pf) {
		diskQueue.add(pf);
	}
	
	private void WritePageToDisk(PageFrame pf) {
		logger.logMessage(ConsoleLogger.Level.INFO, "Escribiendo página " + 
			pf.getPage() + " del proceso " + pf.getFrameOwnerPID() + " al disco");
		pf.setMBit(false);
	}

	private boolean WriteQueueToDisk() {
		boolean write = false;
		for(int i = 0; i < maxWrite && !diskQueue.isEmpty(); i++) {
			WritePageToDisk(diskQueue.poll());
			write = true;
		}
		return write;
	}
	
	private boolean isDue(PageFrame pf) {
		return	Duration.between(
					mmu.getPageFrame(currentPageFrame).getLastReferencedOn(), 
					Instant.now()
				).getNano() > ageThreshold;
	}
	
}
