package ci4821.subsystemsimulator.hardware;

import java.util.ArrayList;
import java.util.List;

public class Memory {

	private List<Integer> pageFrames;
	
	public Memory (Integer size) {
		pageFrames = new ArrayList<>(size);
	}
	
	public synchronized void accessPageFrame(int address) {
		System.out.print("Page Frame " + address + " has been accessed.");
	}

	public List<Integer> getPageFrames() {
		return pageFrames;
	}

	public void setPageFrames(List<Integer> pageFrames) {
		this.pageFrames = pageFrames;
	}
	
}
