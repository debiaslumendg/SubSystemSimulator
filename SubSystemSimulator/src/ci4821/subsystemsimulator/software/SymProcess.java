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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SymProcess implements Runnable {

	private final ConsoleLogger logger;
	private OperatingSystem os;
    private MemoryManagerUnit mmu;
    private PageTable pageTable;
    private ArrayList<Instruction> instrucciones;
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
     * @param rutaInstruccionesProc  Ruta al archivo que contiene los comandos con los que se accederá a la memoria
     */
    public SymProcess(OperatingSystem os,MemoryManagerUnit memoryManagerUnit, String name,
    String rutaInstruccionesProc, int nTextPages, int nDataPages){

    	this.os = os;

        this.name               = name;
        this.nTextPages         = nTextPages;
        this.nDataPages         = nDataPages;
        this.mmu  				= memoryManagerUnit;
        this.pageTable          = new PageTable();

        logger = ConsoleLogger.getInstance();

		instrucciones = new ArrayList<>(30);

		List l = readFileInList(rutaInstruccionesProc);

		Iterator itr = l.iterator();

		String[] linea ;
		while (itr.hasNext()) {
			String instruction = (String) itr.next();
			linea = instruction.split(" ",3);
			if (linea[0].equals("read")) {
				instrucciones.add(new Instruction(Operation.READ,linea));
			}else if(linea[0].equals("write")){
				instrucciones.add(new Instruction(Operation.WRITE,linea));
			}else if(linea[0].equals("repeat")){
				instrucciones.add(new Instruction(Operation.REPEAT,linea));
			}else if(linea[0].equals("end_repeat")){
				instrucciones.add(new Instruction(Operation.END_REPEAT,linea));
			}else{
				System.out.println("Error, instrucción " + instruction + ". No reconocida");
				System.exit(1);
			}
		}
    }


	public static int getRandomDoubleBetweenRange(double min, double max){
		double x = (Math.random()*((max-min)+1))+min;
		return (int) x;
	}
    @Override
    public void run() {

		int valueRead;
		String[] lineaComandos ;

		/**
		 * Controlan la instrucción REPEAT para que funcione.
		 *
		 * repeat <n>
		 * [read|write]  instrucciones a repetir
		 * end_repeat
		 */
		int repeatCountMax		= -1;
		int repeatCount			= -1;
		int repeatLabelInicio 	= -1;

		Instruction instruccion;
		int i = 0;
        while (i < instrucciones.size()) {

			instruccion 		 = 	instrucciones.get(i);
			lineaComandos =	instruccion.getValue();

			if (instruccion.getOperation() == Operation.REPEAT){
				repeatLabelInicio 	= i;
				repeatCountMax		= Integer.parseInt(lineaComandos[1]);
				repeatCount			= 0;
			}else if(instruccion.getOperation() == Operation.END_REPEAT){
				if(++repeatCount != repeatCountMax){
					i = repeatLabelInicio + 1;
					continue;
				}
			}


			try {
				switch(instruccion.getOperation()) {
					case WRITE:
						if(lineaComandos[2].equals("RANDOM")){
							lineaComandos[2] = String.valueOf(getRandomDoubleBetweenRange(0,2000));
						}
						mmu.writeAddress(
								Integer.parseInt(lineaComandos[1]),
								Integer.parseInt(lineaComandos[2]),
								this);
						break;
					case READ:
						valueRead = mmu.readAddress(Integer.parseInt(lineaComandos[1]), this);
						break;
				}

			} catch (PageFaultException e) {
				os.handlePageFault(Integer.parseInt(lineaComandos[1]), this);

				/**
				 * Ejecutamos esta instrucción  de nuevo! Hasta que el SO me lo arregle
				 */
				continue;
			}

			try {
				Thread.sleep((long)(Math.random() * 2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			i ++;
        }
        
    }
    


	public MemoryManagerUnit getMmu() {
		return mmu;
	}


	public PageTable getPageTable() {
		return pageTable;
	}


	public ArrayList<Instruction> getInstrucciones() {
		return instrucciones;
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
	private String[] value;

	public Instruction(Operation op, String[] value) {
		this.op = op;
		this.value = value;
	}

	public Operation getOperation() {
		return op;
	}

	public String[] getValue() {
		return value;
	}
}

enum Operation {
	READ, WRITE,REPEAT,END_REPEAT;
}