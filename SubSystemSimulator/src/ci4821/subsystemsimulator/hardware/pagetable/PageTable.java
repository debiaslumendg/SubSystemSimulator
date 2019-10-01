/**
 *
 * Contiene la clase para la Tabla de Página de un proceso.
 *
 * Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.hardware.pagetable;

public class PageTable {

	private static final int VIRTUAL_PAGES = 16;
	private PageTableEntry[] pageTableEntries;

	public PageTable() {

		pageTableEntries = new PageTableEntry[VIRTUAL_PAGES];

		// Iniciados todas las entradas de la tabla
		for(int i = 0; i < VIRTUAL_PAGES ; i++){
			pageTableEntries[i] = new PageTableEntry();
			pageTableEntries[i].setVirtualPageID(i);
		}
	}

	/**
	 * Dado el identificador de la página virtual accesada se regresa el frame en memoria asociado.
	 * @param virtualPageID Entero que va del 0 al número de páginas virtuales totales menos 1
	 * @return entero que representa el Frame asociado a la página virtual
	 *
	 * Genera excepciones! parte de la simulación así que se deben mostrar al usuario también!
	 */
	public int getFrameID(int virtualPageID) {
		// TODO: CHEQUEAR 0 <= virtualPageID < 16  y lanzar excepcion "SEGMENTATION FAULT" ?
		// TODO: CHEQUEAR SI pageTableEntries[virtualPageID] está siendo usado sino "SEG FAULT" ?
		// TODO: CHEQUEAR SI tiene el frame establecido y lanzar SEG FAULT

		return pageTableEntries[virtualPageID].getFrameID();
	}

	/**
	 * Asigna un frame a una página virtual
	 * @param virtualPageID ID de la página virtual
	 * @param frameID 		ID del frame libre a asignar
	 */
	public void setFrameID(int virtualPageID,int frameID) {
		// TODO: checks para virtualPageID pero solo excepciones al programador!
		pageTableEntries[virtualPageID].setFrameID(frameID);
	}
}
