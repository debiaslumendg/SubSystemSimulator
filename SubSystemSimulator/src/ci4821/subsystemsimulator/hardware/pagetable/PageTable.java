/**
 * Contiene la clase para la Tabla de Página de un proceso.
 *
 * Autores:
 *      Natascha Gamboa     12-11250
 * 	    Manuel  González    11-10390
 * 	    Pedro   Pérez       10-10574
 */
package ci4821.subsystemsimulator.hardware.pagetable;

public class PageTable {

	/**
	 * Cantidad total de páginas virtuales disponibles en la tabla.
	 */
	public static final int PAGES = 16;
	/**
	 * Representacion de la tabla de página.
	 */
	private PageTableEntry[] pageTableEntries;

	public PageTable() {

		pageTableEntries = new PageTableEntry[PAGES];

		// Inicialización de la tabla de pagina
		for(int i = 0; i < PAGES ; i++){
			pageTableEntries[i] = new PageTableEntry();
		}
	}

	/**
	 * Obtiene la entrada asociada a la página.
	 * 
	 * @param processPageID		Entrada en la tabla de página.
	 * @return
	 */
	public PageTableEntry getPage(int processPageID) {
		return pageTableEntries[processPageID];
	}

	/**
	 * Asigna a la página el frame que le fue asignado en la memoria principal.
	 * 
	 * @param processPageID 	Entrada en la tabla de página.
	 * @param pageFrame			Frame asignado en memoria.
	 */
	public void setFrameToPage(int processPageID, int pageFrame) {
		pageTableEntries[processPageID].setFrameID(pageFrame);
	}

	/**
	 * Obtiene el frame asociado a la página.
	 * 
	 * @param processPageID		Entrada en la tabla de página.
	 */
	public int getFrameID(int processPageID) {
		return pageTableEntries[processPageID].getFrameID();
	}
}
