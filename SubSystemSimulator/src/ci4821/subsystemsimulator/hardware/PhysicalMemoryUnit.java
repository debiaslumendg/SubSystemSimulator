package ci4821.subsystemsimulator.hardware;

public class PhysicalMemoryUnit {
    // PHYSICAL MEMORY SIZE IN KB
    public static final int SIZE_KB = 32;
    // NUMBER OF BITS PER WORD
    public static final int WORD_SIZE = 16;
    // PAGE SIZE
    public static final int PAGE_SIZE_KB = 4;
    // CALCULATES THE NUMBER OF ADDRESSES THE MEMORY WILL HOLD
    public static final int N_ADDRESSES = SIZE_KB * 1024 * 8 / WORD_SIZE;

    // REPRESENTS THE PHYSICAL MEMORY
    private char memorySpace[];
    Integer currentAddress;
    MemoryManagerUnit.EStatus status;

    public PhysicalMemoryUnit() {
        memorySpace = new char[N_ADDRESSES - 1];
        currentAddress = 0;
        status = MemoryManagerUnit.EStatus.READY;

        System.out.println("CREADA MEMORIA FISICA");
        System.out.println("CON " + N_ADDRESSES + " DIRECCIONES , [0," + (N_ADDRESSES - 1) + "]"); // prints Hello World
        System.out.println("Number of page frames: " + SIZE_KB / PAGE_SIZE_KB);

    }
    public void set(int address, char value) {
        memorySpace[address] = value;
    }

    public char get(int address) {
        return memorySpace[address];
    }

    enum EStatus {
        READY, READING, WRITING, MOVING
    }

}
