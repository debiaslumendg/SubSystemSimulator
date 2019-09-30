package ci4821.subsystemsimulator;

import ci4821.subsystemsimulator.software.OperatingSystem;


public class Main {


    /**
     * Inicia el simulador
     * @param args
     */
    public static void main(String[] args) {

        /**Referencia al sistema operativo que se encargara a alto nivel ejecutar emulador*/
        OperatingSystem operatingSystem = new OperatingSystem();

        // Inicia un proceso diciendole las páginas
        operatingSystem.startProcess("p1",15,8,"ruta_referencias.txt");
	}

}