/**
 * 
 *  Autores:
 *      Natascha Gamboa      12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */
package ci4821.subsystemsimulator;

import ci4821.subsystemsimulator.software.OperatingSystem;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    /**
     * Inicia el simulador
     * @param args
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); 
        
        System.out.print("Introduzca el tamaño de la memoria principal a simular: ");
        int memorySize = in.nextInt();
        
        System.out.print("Introduzca la cantidad de procesos a simular: ");
        int process = in.nextInt();
        
        OperatingSystem operatingSystem = new OperatingSystem(memorySize);

        // Inicializacion de los procesos
        for(int i = 0; i < process; i++) {

            operatingSystem.startProcess("p" + i, generateRandomStringRef(16));
        }
    }
    
    /**
     * Genera un string de referencia aleatorio entre 0 y un número máximo
     * de páginas
     * 
     * @param maxPages  Máximo número de las páginas del string de referencia
     * @return
     */
    public static ArrayList<Integer> generateRandomStringRef(int maxPages) {

        ArrayList<Integer> stringRef;

        // generacion aleatoria con tamaño aleatorio

        return stringRef;
    }
}