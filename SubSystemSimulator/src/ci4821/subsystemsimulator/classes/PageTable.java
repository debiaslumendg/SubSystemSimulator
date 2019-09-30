/**
 *
 * Contiene la clase para la Página de Tabla de un proceso.
 *
 * Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.classes;


public class PageTable {

	private PageTableEntry[] pageTableEntries;
	private static final int N_VIRTUAL_PAGES = 16;

	public PageTable() {
		pageTableEntries = new PageTableEntry[N_VIRTUAL_PAGES];

		// Iniciados todas las entradas de la tabla
		for(int i = 0; i < N_VIRTUAL_PAGES ; i++){
			pageTableEntries[i].setVirtualPageID(i);
		}
	}

	/**
	 * Dado el identificador de la página virtual accesada se regresa el frame en memoria asociado.
	 * @param virtualPageID Entero que va del 0 al número de páginas virtuales totales menos 1
	 * @return entero que representa el Frame asociado a la página virtual
	 *
	 * Genera excepcions! parte de la simulación así que se deben mostrar al usuario también!
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
