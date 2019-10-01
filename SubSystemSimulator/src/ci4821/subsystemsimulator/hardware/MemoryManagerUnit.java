/**
 * 
 */
package ci4821.subsystemsimulator.hardware;

public class MemoryManagerUnit {
    
    public static final int SIZE = 32;
    // NUMBER OF BITS PER WORD
    //public static final int WORD_SIZE = 16;
    public static final int PAGE_SIZE = 4;
    private MemoryEntry[] memoryEntries;

    public MemoryManagerUnit() {
        memoryEntries = new MemoryEntry[SIZE / PAGE_SIZE];
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
