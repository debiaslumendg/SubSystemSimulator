package ci4821.subsystemsimulator.software;

import ci4821.subsystemsimulator.hardware.MemoryEntry;

import java.util.HashMap;

/**
 * ClockAlgorithm
 */
public class ClockAlgorithm {

    private int pointer, pageFaults, page, refStrLegnth;
    private HashMap<MemoryEntry, Boolean> processes;
}