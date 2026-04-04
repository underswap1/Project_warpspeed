package dev.underswap1.projectwarpspeed.machine;

import java.util.EnumMap;

public class MachineInventory {

    private final EnumMap<MachinePartType, MachinePart> parts = new EnumMap<>(MachinePartType.class);

    public void setPart(MachinePart part) {
        parts.put(part.type(), part);
    }

    public boolean hasPart(MachinePartType type) {
        return parts.containsKey(type);
    }

    public Iterable<MachinePart> getParts() {
        return parts.values();
    }
}