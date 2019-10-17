/**
 * 
 *  Autores:
 *      Natascha Gamboa      12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator;

import java.util.Scanner;

import ci4821.subsystemsimulator.software.OperatingSystem;

public class Main {

    /**
     * Inicia el simulador
     * @param args
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); 
        
        System.out.print("Introduzca el tamaño de la memoria principal a simular(en frames):  ");
        int memorySize = in.nextInt();
        
        System.out.print("Introduzca la cantidad de procesos a simular:  ");
        int process = in.nextInt();
        
        OperatingSystem os = new OperatingSystem(memorySize);

        // Inicializacion de los procesos
        for(int i = 0; i < process; i++) {
        	System.out.print("Introduzca el tamaño del proceso " + i + ":  ");
        	os.createProcess(in.nextInt());
        }
        in.close();
        
        os.startProcesses();
        
        for (Thread p : os.getProcesses()) {
        	try {
				p.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

}