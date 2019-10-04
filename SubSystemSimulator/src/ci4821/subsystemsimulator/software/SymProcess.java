/**
 *
 * Proceso a emular representado como un hilo para su ejecuciÃ³n.
 *
 *  Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  GonzÃ¡lez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import java.util.ArrayList;
import java.util.Iterator;

public class SymProcess implements Runnable {

	private OperatingSystem os;
    private MemoryManagerUnit mmu;
    private PageTable pageTable;
    private ArrayList<Instruction> stringRef;
    private String name;
    private int nTextPages, nDataPages;
    private int current = 0;

    /**
     * Crea el proceso
     * @param name      Nombre del proceso
     * @param rutaRefs  Ruta al archivo que contiene las referencias a acceder en memoria
     */
    public SymProcess(MemoryManagerUnit memoryManagerUnit, String name,
    String rutaRefs, int nTextPages, int nDataPages){

        this.name               = name;
        this.nTextPages         = nTextPages;
        this.nDataPages         = nDataPages;
        this.mmu  = memoryManagerUnit;
        this.pageTable          = new PageTable();

        //TODO: Leer rutaRefs
        stringRef = new ArrayList<>(30);
    }


    @Override
    public void run() {
        
		/*
		 * int i = 5; int frameID = -1;
		 */
    	
        for (Instruction i : stringRef) {
        	
        	boolean success = false;
        	do {
	        	try {
		        	switch(i.getOp()) {
		        		case WRITE:
		        			mmu.writeAddress(i.getPage(), this);
		        			break;
		        		case READ:
		        			mmu.readAddress(i.getPage(), this);
		        			break;
		        	}
		        	success = true;
	        	} catch (PageFaultException e) {
	        		os.handlePageFault(i.getPage(), this);
	        	}
        	} while (!success);
			/*
			 *  if(i-- == 1)  i = 5  // TODO: Dormir tiempo aleatorio para hacerlo mÃ¡s real
			 *  //Thread.sleep()  
			 */
        }
        
    }
    


	public MemoryManagerUnit getMmu() {
		return mmu;
	}


	public PageTable getPageTable() {
		return pageTable;
	}


	public ArrayList<Instruction> getStringRef() {
		return stringRef;
	}


	public String getName() {
		return name;
	}


	public int getnTextPages() {
		return nTextPages;
	}


	public int getnDataPages() {
		return nDataPages;
	}
}

class Instruction {
	
	private Operation op;
	private int page;
	
	public Instruction(Operation op, int page) {
		this.op = op;
		this.page = page;
	}

	public Operation getOp() {
		return op;
	}

	public int getPage() {
		return page;
	}
	
}

enum Operation {
	READ, WRITE
}