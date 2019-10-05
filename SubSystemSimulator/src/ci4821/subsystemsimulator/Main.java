package ci4821.subsystemsimulator;

import ci4821.subsystemsimulator.software.OperatingSystem;


public class Main {


    /**
     * Inicia el simulador
     * @param args
     */
    public static void main(String[] args) {

        /** Simulador*/
        OperatingSystem operatingSystem = new OperatingSystem();

        // Inicializacion de los procesos
        operatingSystem.startProcess("p1",3,2,"p1.txt");
	}

}