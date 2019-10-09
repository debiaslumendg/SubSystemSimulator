/**
 *
 * Representa la tabla de disco que se supone contendría entradas:
 *
 * [Pagina virtual ID]  -> [Direccion en disco]
 *
 * En nuestro caso solo guarda el contenido establecido por el proceso con un WRITE. Cuando un frame es reemplazado de
 * memoria su contenido se guarda en la instancia de esta clase que le pertenezca al proceso al que le pertenecia
 * el frame que le quitaron.
 *
 *  Autores:
 *      Natscha Gamboa      12-11250
 * 	    Manuel  GonzÃ¡lez   11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.pagetable.PageTable;

public class SwapTable {

    SwapTableEntry[] tableEntries = new SwapTableEntry[PageTable.VIRTUAL_PAGES];

    SwapTable(){

        for(int i = 0; i < PageTable.VIRTUAL_PAGES;i++){
            tableEntries[i] = new SwapTableEntry();
        }
    }

    public void set(int pos, int newValue) {
        tableEntries[pos].setValueInDisk(newValue);
    }

    public SwapTableEntry getEntry(int pageID) {
        return tableEntries[pageID];
    }
}
