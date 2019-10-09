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

	public PageTableEntry getPage(int pageIndex) {
		return pageTableEntries[pageIndex];
	}

	/**
	 * Asigna a la entrada de tabla de página una página del string de
	 * referencia del proceso
	 * @param processPageID Página del string de referencia del proceso
	 * @param pageIndex		Entrada en la tabla de página
	 */
	public void setPage(int processPageID, int pageIndex) {
		pageTableEntries[pageIndex].setProcessPageID(processPageID);
	}
}
