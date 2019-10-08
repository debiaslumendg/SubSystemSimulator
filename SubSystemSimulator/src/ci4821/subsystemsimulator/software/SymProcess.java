/**
 *
 * Proceso a emular representado como un hilo para su ejecuciÃ³n.
 *
 *  Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;
import ci4821.subsystemsimulator.exceptions.PageFaultException;
import ci4821.subsystemsimulator.hardware.MemoryManagerUnit;
import ci4821.subsystemsimulator.util.ConsoleLogger;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SymProcess implements Runnable {

	private final ConsoleLogger logger;
	private OperatingSystem os;
    private MemoryManagerUnit mmu;
    private PageTable pageTable;
    private ArrayList<Instruction> stringRef;
    private String name;
    private int nTextPages, nDataPages;
    private int current = 0;
	private Long pid;

	public static List<String> readFileInList(String fileName)
	{
		List<String> lines = Collections.emptyList();
		try{
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		}catch (IOException e)
		{
			// do something
			e.printStackTrace();
		}
		return lines;
	}

	/**
     * Crea el proceso
     * @param name      Nombre del proceso
     * @param rutaRefs  Ruta al archivo que contiene las referencias a acceder en memoria
     */
    public SymProcess(OperatingSystem os,MemoryManagerUnit memoryManagerUnit, String name,
    String rutaRefs, int nTextPages, int nDataPages){

    	this.os = os;

        this.name               = name;
        this.nTextPages         = nTextPages;
        this.nDataPages         = nDataPages;
        this.mmu  				= memoryManagerUnit;
        this.pageTable          = new PageTable();

        logger = ConsoleLogger.getInstance();

		stringRef = new ArrayList<>(30);

		List l = readFileInList(rutaRefs);

		Iterator<String> itr = l.iterator();

		String[] linea ;
		while (itr.hasNext()) {
			String instruction = itr.next();
			linea = instruction.split(" ",3);
			if (linea[0].equals("read")) {
				stringRef.add(new Instruction(Operation.READ,Integer.parseInt(linea[1])));
				//System.out.println("Agregada instruccion READ con " +  linea[0] + " " + linea[1] );
			}else if(linea[0].equals("write")){
				stringRef.add(
						new Instruction(
								Operation.WRITE,
								Integer.parseInt(linea[1]),
								Integer.parseInt(linea[2])
						));
				//System.out.println("Agregada instruccion WRITE con " +  linea[0] + " " + linea[1] + " " + linea[2]);
			}
		}
    }


    @Override
    public void run() {
        
		/*
		 * int i = 5; int frameID = -1;
		 */

		int valueRead;
        for (Instruction i : stringRef) {

        	boolean success = false;
        	do {
	        	try {
		        	switch(i.getOp()) {
		        		case WRITE:
		        			mmu.writeAddress(i.getPage(), i.getValue(),this);
		        			break;
		        		case READ:
							valueRead = mmu.readAddress(i.getPage(), this);
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

	public void setPID(Long newPid) {
		this.pid = newPid;
	}

	public Long getPID() {
		return pid;
	}
}

class Instruction {
	
	private Operation op;
	private int page;
	private int value;


	public Instruction(Operation op, int page) {
		this(op,page,-1);
	}
	public Instruction(Operation op, int page,int value) {
		this.op = op;
		this.page = page;
		this.value = value;
	}

	public Operation getOp() {
		return op;
	}

	public int getPage() {
		return page;
	}

	public int getValue() {
		return value;
	}
}

enum Operation {
	READ, WRITE
}