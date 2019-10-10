/**
 * Proceso a emular representado como un hilo para su ejecucion.
 *
 *  Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;

import java.util.ArrayList;

public class SymProcess implements Runnable {

	private OperatingSystem so;
    private PageTable pageTable;
    private ArrayList<Integer> stringRef;
    private String name;
	private Long pid;

	/**
     * Crea el proceso
     * @param name      Nombre del proceso
     * @param rutaRefs  Ruta al archivo que contiene las referencias a acceder en memoria
     */
    public SymProcess(String name, String stringRef, OperatingSystem so){

        this.name       = name;
		this.so			= so;
		this.pageTable  = new PageTable();
		this.stringRef 	= new ArrayList<>(stringRef.length());
    }

    @Override
    public void run() {

		for(Integer page : stringRef) {

			so.asingMemory(page,this);
		}
    }
    
	public PageTable getPageTable() {
		return pageTable;
	}

	public ArrayList<Integer> getStringRef() {
		return stringRef;
	}

	public String getName() {
		return name;
	}

	public void setPID(Long newPid) {
		this.pid = newPid;
	}

	public Long getPID() {
		return pid;
	}
}