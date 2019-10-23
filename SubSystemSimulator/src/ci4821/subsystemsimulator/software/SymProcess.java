/**
 * Proceso a emular representado como un hilo para su ejecucion.
 *
 *  Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.software;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ci4821.subsystemsimulator.util.ConsoleLogger;

public class SymProcess implements Runnable {

	private final ConsoleLogger logger;
	private OperatingSystem so;
    private List<Integer> references;
	private Long pid;
	
    public SymProcess(int size, OperatingSystem so){
		this.so			= so;
		this.references = (new Random()).ints(size, 0, size).boxed().collect(Collectors.toList());
        logger = ConsoleLogger.getInstance();
    }
    
    @Override
    public void run() {
    	
		for(Integer page : references) {
			logger.logMessage(ConsoleLogger.Level.INFO, "Página " + page + " del proceso " + pid +
				" pide memoria.");
			so.referencePage(page,this);
		}

    }

	public Long getPID() {
		return pid;
	}

	public void setPID(Long pid) {
		this.pid = pid;
	}
}