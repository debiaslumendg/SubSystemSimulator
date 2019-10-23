/**
 * Registra estadisticas de los procesos
 *
 *  Autores:
 *      Natascha Gamboa      12-11250
 * 	    Manuel  Gonzalez    11-10390
 * 	    Pedro   Perez       10-10574
 */package ci4821.subsystemsimulator.util;

import java.util.HashMap;
import java.util.Map;

public class LogStats {

    public enum StatType {
        PAGE_FAULT, // Numero de Veces que el proceso p genero Page Faults
        REFS_MEM, // Numero de Veces que el proceso p acceso a la memoria
        WSCLOCK_, // Numero de Veces que el proceso p al hacer un acceso a la memoria provocó un reemplazo
        WSCLOCK_TARGET, // Numero de Veces que un frame del proceso p fue target para reemplazo
        WRITE_DISK, // Numero de Veces que frames de proceso p fueron escritos a disco
    }

    private static volatile LogStats sSoleInstance = new LogStats();

    private Map<Long, HashMap<StatType,Integer>> STATS = new HashMap<>();

    //private constructor.
    private LogStats(){
    }

    public static LogStats getInstance() {
        return sSoleInstance;
    }

    public void addProcessToStats(Long pid){
        if (!STATS.containsKey(pid)){
            STATS.put(pid,new HashMap<>());
        }
    }

    public void incrementStat(Long pid,StatType statType){
        addProcessToStats(pid);

        HashMap<StatType, Integer> hashMap = STATS.get(pid);

        int statValue = hashMap.getOrDefault(statType,0);
        hashMap.put(statType,statValue + 1);
    }

    public void showStats(){

        HashMap<StatType, Integer> hashMap;


        System.out.print("\n\n");
        System.out.format(
                "\033[1;32m>\033[1;37m %5s|%17s|%12s|%21s|%30s|%23s\n",
                "PID",
                convertLogLevelToString(StatType.REFS_MEM),
                convertLogLevelToString(StatType.PAGE_FAULT),
                convertLogLevelToString(StatType.WSCLOCK_),
                convertLogLevelToString(StatType.WSCLOCK_TARGET),
                convertLogLevelToString(StatType.WRITE_DISK)
        );

        for(Long pid : STATS.keySet()){

            hashMap = STATS.get(pid);

            System.out.format(
                    "\033[1;32m>\033[1;37m %5s|%17s|%12s|%21s|%30s|%23s\n",
                    pid,
                    hashMap.getOrDefault(StatType.REFS_MEM,0),
                    hashMap.getOrDefault(StatType.PAGE_FAULT,0),
                    hashMap.getOrDefault(StatType.WSCLOCK_,0),
                    hashMap.getOrDefault(StatType.WSCLOCK_TARGET,0),
                    hashMap.getOrDefault(StatType.WRITE_DISK,0)
            );
        }

    }

    private String convertLogLevelToString(StatType statType) {
        switch (statType){
            case PAGE_FAULT:
                return "Page Faults";
            case REFS_MEM:
                return "Accesos a memoria";
            case WSCLOCK_:
                return "Reemplazos provocados";
            case WSCLOCK_TARGET:
                return "Frames reemplazados en memoria";
            case WRITE_DISK:
                return "Frames escritos a disco";
            default:
                throw new IllegalStateException("Unexpected value: " + statType);
        }
    }
}
