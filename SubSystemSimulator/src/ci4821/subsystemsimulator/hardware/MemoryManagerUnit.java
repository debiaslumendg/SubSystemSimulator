package ci4821.subsystemsimulator.hardware;

public class MemoryManagerUnit {
    // PHYSICAL MEMORY SIZE IN KB
    public static final int SIZE_KB = 32;
    // NUMBER OF BITS PER WORD
    //public static final int WORD_SIZE = 16;
    // PAGE SIZE
    public static final int PAGE_SIZE_KB = 4;

    // REPRESENTS THE PHYSICAL MEMORY
    private PhysicalMemoryEntry[] physicalMemoryEntries;

    public MemoryManagerUnit() {
        physicalMemoryEntries = new PhysicalMemoryEntry[SIZE_KB / PAGE_SIZE_KB];
    }

    /**
     * Utilizamos un método monitor para que solo 1 proceso acceda a la memoria.
     * @param frameID ID del frame accedido
     */
    synchronized public void accessAddress(int frameID) {
        // TODO: falta
    }

    /**
     * Este pageFaultHandler no lo veo correcto estando miembro de PhysicalMemoryUnit?
     * Pero si consideramos que se tienen que sincronizar los procesos para que
     * dos procesos no accedan a este método y que si un método está aquí no tiene que estar en
     * accessAddress ... TODO: falta pensarlo más aquí
     * @param processID
     * @param frameID
     */

    synchronized public void pageFaultHandler(int processID,int frameID) {
        // TODO: falta
    }

}
