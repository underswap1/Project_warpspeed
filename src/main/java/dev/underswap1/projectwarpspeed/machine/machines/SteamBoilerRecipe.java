package dev.underswap1.projectwarpspeed.machine.machines;

import dev.underswap1.projectwarpspeed.machine.MachinePartType;

import java.util.HashMap;
import java.util.Map;

public class SteamBoilerRecipe {
    public static final Map<MachinePartType, Integer> REQUIRED = new HashMap<>();
    static {
        REQUIRED.put(MachinePartType.PLATE, 8);
        REQUIRED.put(MachinePartType.REINFORCED_PLATE, 2);
        REQUIRED.put(MachinePartType.SUPPORT, 2);

        REQUIRED.put(MachinePartType.BOLT, 4);
        REQUIRED.put(MachinePartType.RIVET, 2);
        REQUIRED.put(MachinePartType.BRACKET, 2);

        REQUIRED.put(MachinePartType.PIPE, 3);
        REQUIRED.put(MachinePartType.VALVE, 2);
        REQUIRED.put(MachinePartType.CYLINDER, 1);
        REQUIRED.put(MachinePartType.PISTON, 1);
        REQUIRED.put(MachinePartType.MANIFOLD, 1);

        REQUIRED.put(MachinePartType.COIL, 2);
        REQUIRED.put(MachinePartType.HEATING_ELEMENT, 1);
        REQUIRED.put(MachinePartType.BURNER, 1);
        REQUIRED.put(MachinePartType.THERMAL_LINING, 1);
    }
}
