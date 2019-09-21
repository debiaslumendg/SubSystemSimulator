package ci4821.subsystemsimulator.hardware;

import java.util.ArrayList;
import java.util.List;

import static ci4821.subsystemsimulator.hardware.PhysicalMemoryUnit.WORD_SIZE;

public class MemoryManagerUnit {

	private static final int N_BITS_PAGES = 4; // 2 ** 4 == 16
	private static final int PAGE_NUMBER_MASK = 61440;

	private PhysicalMemoryUnit memoryRam;
	Integer currentAddress;
	EStatus status;

	public MemoryManagerUnit() {
		currentAddress = 0;
		// PHYSICAL MEMORY UNIT
		memoryRam = new PhysicalMemoryUnit();
		status = EStatus.READY;
	}

	public void set(int address, char value) {
		int vpageNumber = (address & PAGE_NUMBER_MASK) >> WORD_SIZE - N_BITS_PAGES;
		System.out.println("Virtual page number: " + vpageNumber);
		// BUSCAR FRAME PAGE NUMBER EN LA PAGE TABLA QUE ESTA EN PHYSICALMEMORYUNIT vpageNumber
		// BUSCAR EN LA TLB
		// PUEDE OCURRIR PAGE FAULT
		// EN CASO DE PF SO : -> PASOS SO QUE HAY QUE CREAR (swapping y algo de reemplazo)
		// CREAR LA REAL DIRECCION EN LA MEMORIA REAL

		// ESTABLECER/ OBTENER EL VALOR EN LA MEMORIA
		// ACTUALIZAR VALORES EN LA PAGE TABLE

		memoryRam.set(100,'H');

	}

	public char get(int address) {
		return memoryRam.get(address);
	}


	enum EStatus {
		READY, READING, WRITING, MOVING
	}
	
}
