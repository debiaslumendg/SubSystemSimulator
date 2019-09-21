package ci4821.subsystemsimulator.hardware;

public class Processor {

    // MEMORY MANAGER UNIT
    private MemoryManagerUnit mmu   = new MemoryManagerUnit();

    public void set(int address, char value) {
        mmu.set(address,value);
    }

    public char get(int address) {
        return mmu.get(address);
    }
}
