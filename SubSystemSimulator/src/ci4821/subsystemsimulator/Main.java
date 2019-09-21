package ci4821.subsystemsimulator;


public class Main {

	public static void main(String[] args) {
		// PHYSICAL MEMORY SIZE IN KB
		int PM_SIZE_KB = 32;

		// PAGE SIZE
		int PAGE_SIZE_KB = 4;

		// NUMBER OF VIRTUAL PAGES
		int N_PAGES = 16;
        int N_BITS_PAGES = 4; // 2 ** 4 == 16

		// NUMBER OF BITS PER WORD
		int WORD_SIZE = 16;

		int NVADRESSES = (int) Math.pow(2, WORD_SIZE);

		// CALCULATES THE NUMBER OF ADDRESSES THE MEMORY WILL HOLD
		int NADRESSES = PM_SIZE_KB * 1024 * 8 / WORD_SIZE;

		// REPRESENTS THE PHYSICAL MEMORY
		char pmemory[];
		pmemory = new char[NADRESSES - 1];

        int PAGE_NUMBER_MASK = 61440;

		System.out.println("CREADA MEMORIA FISICA");
		System.out.println("CON " + NADRESSES + " DIRECCIONES , [0," + (NADRESSES - 1) + "]"); // prints Hello World
		System.out.println("Number of page frames: " + PM_SIZE_KB / PAGE_SIZE_KB);

		System.out.println();
		System.out.println("CREADA MEMORIA VIRTUAL CON " + NVADRESSES + " DIRECCIONES"); // prints Hello World
		System.out.println("[0," + (NVADRESSES - 1) + "]"); // prints Hello World
		System.out.println("Number of pages: " + N_PAGES);

		// SET 'P1' 8192
        // SO -> INICIAR PROCESO P
        // MMU-> CREAR PT PARA PROCESO P
        // SO -> CREAR DISK ADRESSES TABLE PARA EL PROCESO P
        int param = 8192;

        int vpageNumber = (param & PAGE_NUMBER_MASK) >> WORD_SIZE - N_BITS_PAGES;

        System.out.println("Virtual page number: " + vpageNumber);

        // BUSCAR FRAME PAGE NUMBER EN LA PAGE TABLA QUE ESTA EN PHYSICALMEMORYUNIT vpageNumber
        // BUSCAR EN LA TLB
        // PUEDE OCURRIR PAGE FAULT
        // EN CASO DE PF SO : -> PASOS SO QUE HAY QUE CREAR (swapping y algo de reemplazo)
        // CREAR LA REAL DIRECCION EN LA MEMORIA REAL

        // ESTABLECER/ OBTENER EL VALOR EN LA MEMORIA
        // ACTUALIZAR VALORES EN LA PAGE TABLE
		pmemory[100] = 'H';
		System.out.println(pmemory[100]); // prints Hello World
	}

}