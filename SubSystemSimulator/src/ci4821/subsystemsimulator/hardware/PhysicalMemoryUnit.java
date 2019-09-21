package ci4821.subsystemsimulator.hardware;

import java.util.ArrayList;
import java.util.List;

public class PhysicalMemoryUnit {
    List<Integer> memorySpace;
    Integer currentAddress;
    MemoryManagerUnit.EStatus status;

    public PhysicalMemoryUnit(Integer size) {
        memorySpace = new ArrayList<>(size);
        currentAddress = 0;
        status = MemoryManagerUnit.EStatus.READY;
    }

    enum EStatus {
        READY, READING, WRITING, MOVING
    }

}
