/**
 * 
 *  Autores:
 *      Natascha Gamboa      12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator;

import ci4821.subsystemsimulator.software.OperatingSystem;

public class Main {

    /**
     * Inicia el simulador
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Simulador
         */
        OperatingSystem operatingSystem = new OperatingSystem();

        // Inicializacion de los procesos
        operatingSystem.startProcess("p1","1 4 5 12 6");
	}

}