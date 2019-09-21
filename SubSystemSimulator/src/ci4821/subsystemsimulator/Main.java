package ci4821.subsystemsimulator;


import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;

import static ci4821.subsystemsimulator.hardware.PhysicalMemoryUnit.WORD_SIZE;

public class Main {


    public static void main(String[] args) {

        // MEMORY MANAGER UNIT
        MemoryManagerUnit mmu = new MemoryManagerUnit();

		// NUMBER OF VIRTUAL PAGES
		int N_PAGES = 16;


        int NVADDRESSES = (int) Math.pow(2, WORD_SIZE);

		System.out.println();
		System.out.println("CREADA MEMORIA VIRTUAL CON " + NVADDRESSES + " DIRECCIONES"); // prints Hello World
		System.out.println("[0," + (NVADDRESSES - 1) + "]"); // prints Hello World
		System.out.println("Number of pages: " + N_PAGES);

        // SO -> INICIAR PROCESO P
        // MMU-> CREAR PT PARA PROCESO P
        // SO -> CREAR DISK ADDRESSES TABLE PARA EL PROCESO P
        // SET 'P1' 8192
        int param = 8192;
        mmu.set(100,'H');
        System.out.println(mmu.get(100)); // prints Hello World
	}

}