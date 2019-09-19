package ci4821.subsystemsimulator.hardware;

import java.util.ArrayList;
import java.util.List;

public class Memory {

	List<Integer> memorySpace;
	Integer currentAddress;
	EStatus status;
	
	public Memory (Integer size) {
		memorySpace = new ArrayList<>(size);
		currentAddress = 0;
		status = EStatus.READY;
	}
	
	enum EStatus {
		READY, READING, WRITING, MOVING
	}
	
}
