package ci4821.subsystemsimulator.hardware;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import ci4821.subsystemsimulator.software.SymProcess;

public class Processor {

	Memory mainMemory;
	Queue<SymProcess> processes;
	List<Core> cores;
	
	boolean processWaiting = false;
	boolean queueAvailable = false;
	
	public Processor(int cores, int nsquantum, Memory mainMemory) {
		this.mainMemory = mainMemory;
		processes = new ArrayDeque<>();
	}
	
	public synchronized void dequeueProcess(Core c, SymProcess process) {
		while (!queueAvailable) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
		queueAvailable = false;
		processes.add(c.getCurrentProcess());
		c.setCurrentProcess(processes.remove());
		
	}
	
	public synchronized void queueProcess(Core c, SymProcess process) {
		while (!queueAvailable) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
		queueAvailable = false;
		processes.add(c.getCurrentProcess());
		c.setCurrentProcess(processes.remove());
		
	}
	
	public synchronized boolean isProcessWaiting() {
		return processWaiting;
	}
	
	
	private class Core implements Runnable {
		
		SymProcess currentProcess;
		
		public Core() {
			
		}
		
		public void run() {
			while(isProcessWaiting()) {
				
			}
		}


		public SymProcess getCurrentProcess() {
			return currentProcess;
		}


		public void setCurrentProcess(SymProcess currentProcess) {
			this.currentProcess = currentProcess;
		}
		
		
	}
}
