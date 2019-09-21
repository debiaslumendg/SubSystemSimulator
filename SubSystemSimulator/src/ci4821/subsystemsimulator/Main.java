package ci4821.subsystemsimulator;


import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.hardware.OperatingSystem;
import ci4821.subsystemsimulator.hardware.Processor;

import static ci4821.subsystemsimulator.hardware.PhysicalMemoryUnit.WORD_SIZE;

public class Main {


    public static void main(String[] args) {

        OperatingSystem operatingSystem = new OperatingSystem();
        Processor processor     = new Processor();

        operatingSystem.setReferenceProcessor(processor);

		// NUMBER OF VIRTUAL PAGES
		int N_PAGES = 16;

        int NVADDRESSES = (int) Math.pow(2, WORD_SIZE);

		System.out.println();
		System.out.println("CREADA MEMORIA VIRTUAL CON " + NVADDRESSES + " DIRECCIONES"); // prints Hello World
		System.out.println("[0," + (NVADDRESSES - 1) + "]"); // prints Hello World
		System.out.println("Number of pages: " + N_PAGES);

        // SO -> INICIAR PROCESO P
        operatingSystem.startProcess("p1",2);
        // P  -> SET  8192 to 'H'
        // Processor -> SET  8192 to 'H'
        processor.set(8192,'H');
        System.out.println(processor.get(8192)); // prints Hello World
	}

}