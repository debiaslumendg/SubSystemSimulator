package ci4821.subsystemsimulator.hardware;

import java.util.ArrayList;
import java.util.List;

public class MemoryManagerUnit {

	List<Integer> memorySpace;
	Integer currentAddress;
	EStatus status;
	
	public MemoryManagerUnit(Integer size) {
		memorySpace = new ArrayList<>(size);
		currentAddress = 0;
		status = EStatus.READY;
	}
	
	enum EStatus {
		READY, READING, WRITING, MOVING
	}
	
}
