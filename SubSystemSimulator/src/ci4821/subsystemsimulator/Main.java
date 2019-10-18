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
        
        System.out.print("\033[1;37mTamaño de la memoria principal a simular(en frames): ");
        int memorySize = in.nextInt();
        
        System.out.print("Cantidad de procesos a simular: ");
        int process = in.nextInt();
        
        OperatingSystem os = new OperatingSystem(memorySize);

        // Inicializacion de los procesos
        for(int i = 0; i < process; i++) {
        	System.out.print("Tamaño del proceso " + i + " (en páginas): ");
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