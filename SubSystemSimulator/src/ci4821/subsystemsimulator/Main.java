package ci4821.subsystemsimulator;

import ci4821.subsystemsimulator.software.OperatingSystem;
import ci4821.subsystemsimulator.util.ConsoleLogger;


public class Main {


    /**
     * Inicia el simulador
     * @param args
     */
    public static void main(String[] args) {

        /** Simulador*/
        OperatingSystem operatingSystem = new OperatingSystem();

        // Inicializacion del proceso 1
        operatingSystem.startProcess("proc1",13,2,"test/dosprocesospeleones/p1.txt");
        // Inicializacion del proceso 2
        operatingSystem.startProcess("proc2",10,2,"test/dosprocesospeleones/p2.txt");
	}

}