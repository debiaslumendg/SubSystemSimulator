/**
 * Logger para los mensajes del emulador
 *
 *  Autores:
 *      Natascha Gamboa      12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */

package ci4821.subsystemsimulator.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger {

    public enum Level {
        INFO,
        PAGE_FAULT,
        ERROR,
        MEM_PAGE,
        ASIG_PAGE,
        WRITE_DISK,
        NUEVO_PROCESO,
        PROCESO_INICIADO
    }

    private static volatile ConsoleLogger sSoleInstance = new ConsoleLogger();

    //private constructor.
    private ConsoleLogger(){}

    public static ConsoleLogger getInstance() {
        return sSoleInstance;
    }


    public void logMessage(Level level,String message){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy | hh:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        Thread t = Thread.currentThread();

        boolean isOperatingSystemLogging = t.getName().equals("main");

        if(level == Level.PAGE_FAULT){
            // Si es un page fault mentimos diciendo que es el hilo que llamo a este logger fue el main,
            // el cual está representado por el SO.
            // Se pudiera hacer pasando el llamante por parametros pero esta es una solución creo mejor y sencilla.
            isOperatingSystemLogging = true;
        }

        System.out.format(
                "> %s | %s | %s | %s | \"%s\"\n",
                dtf.format(now),
                isOperatingSystemLogging? "SO":"Proceso: " + t.getName() +" ",
                isOperatingSystemLogging? "* ":"PID: " + t.getId(),
                convertLogLevelToString(level),
                message
        );
    }

    private String convertLogLevelToString(Level level) {
        switch (level){

            case INFO:
                return "Info";
            case PAGE_FAULT:
                return "Page Fault";
            case ERROR:
                return "Error";
            case MEM_PAGE:
                return "Pagina pide memoria";
            case ASIG_PAGE:
                return "Asignando memoria";
            case WRITE_DISK:
                return "Actualizar datos";
            case NUEVO_PROCESO:
                return "Nuevo Proc";
            case PROCESO_INICIADO:
                return "Proc Inic";
            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }
    }
}
