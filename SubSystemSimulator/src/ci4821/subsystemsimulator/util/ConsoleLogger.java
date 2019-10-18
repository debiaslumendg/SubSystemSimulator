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
                "\033[1;32m>\033[1;37m %s | %s | %s | %s | \"%s\"\n",
                dtf.format(now),
                isOperatingSystemLogging? "\033[1;36mSistema Operación\033[1;37m":"\033[1;32mProceso: " + t.getName() + "\033[1;37m",
                isOperatingSystemLogging? "   *   ":"PID: " + t.getId(),
                convertLogLevelToString(level),
                message
        );
    }

    private String convertLogLevelToString(Level level) {
        switch (level){

            case INFO:
                return "\033[1;32mInfo\033[1;37m";
            case PAGE_FAULT:
                return "\033[1;31m \tPage Fault\t \033[1;37m";
            case ERROR:
                return "\033[1;31mError\033[1;37m";
            case MEM_PAGE:
                return "\033[1;35m Proceso pide memoria \033[1;37m";
            case ASIG_PAGE:
                return "\033[1;35m   Asignando memoria  \033[1;37m";
            case WRITE_DISK:
                return "\033[1;34m   Actualizar datos   \033[1;37m";
            case NUEVO_PROCESO:
                return "\033[1;36mNuevo Proc\033[1;37m";
            case PROCESO_INICIADO:
                return "\033[1;36mProc Inic\033[1;37m";
            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }
    }
}
