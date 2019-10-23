package ci4821.subsystemsimulator.util;

import java.util.HashMap;
import java.util.Map;

public class LogStats {

    public enum StatType {
        PAGE_FAULT,
        REFS_MEM,
        WSCLOCK_,
        WRITE_DISK,
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
                "\033[1;32m>\033[1;37m %5s|%20s|%20s|%20s|%20s\n",
                "PID",
                convertLogLevelToString(StatType.REFS_MEM),
                convertLogLevelToString(StatType.PAGE_FAULT),
                convertLogLevelToString(StatType.WSCLOCK_),
                convertLogLevelToString(StatType.WRITE_DISK)
        );

        for(Long pid : STATS.keySet()){

            hashMap = STATS.get(pid);

            System.out.format(
                    "\033[1;32m>\033[1;37m %5s|%20s|%20s|%20s|%20s\n",
                    pid,
                    hashMap.getOrDefault(StatType.REFS_MEM,0),
                    hashMap.getOrDefault(StatType.PAGE_FAULT,0),
                    hashMap.getOrDefault(StatType.WSCLOCK_,0),
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
            case WRITE_DISK:
                return "Escrituras a discos";
            default:
                throw new IllegalStateException("Unexpected value: " + statType);
        }
    }
}
