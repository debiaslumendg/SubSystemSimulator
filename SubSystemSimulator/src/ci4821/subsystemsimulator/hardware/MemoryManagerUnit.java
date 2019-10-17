/**
 * Clase que representa la memoria principal
 */
package ci4821.subsystemsimulator.hardware;

import ci4821.subsystemsimulator.software.SymProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryManagerUnit {

    private List<PageFrame> mainMemory; // Representacion de los frames de la memoria principal.
    private int lastFreeFrame = 0; // Ultimo frame asignado en la memoria

    public MemoryManagerUnit(int totalFrames) {
        this.mainMemory = new ArrayList<>(Collections.nCopies(totalFrames, new PageFrame()));
    }

    public void assignPageFrame(int pageFrame, int processPage, SymProcess p) {
    	mainMemory.get(pageFrame).assign(p.getPID(), processPage);
    	if (pageFrame == lastFreeFrame) lastFreeFrame++;
    }
    
    public void freePageFrame(int pageFrame) {
    	mainMemory.get(pageFrame).evict();
    }
    
    public boolean hasFreeFrames() {
    	return lastFreeFrame != mainMemory.size();
    }
    
    public PageFrame getPageFrame(int pageFrame) {
    	return mainMemory.get(pageFrame);
    }
    
    public int getLastFreeFrame() {
    	return lastFreeFrame;
    }
}
