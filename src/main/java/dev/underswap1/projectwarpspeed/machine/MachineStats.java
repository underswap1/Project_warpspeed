package dev.underswap1.projectwarpspeed.machine;

import dev.underswap1.projectwarpspeed.materials.core.MaterialProperties;

@SuppressWarnings("unused")
public class MachineStats {

    public static double getCooling(MachineInventory inv) {
        double cooling = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case BLADE, TURBINE_BLADE, IMPELLER, HEAT_SINK, RADIATOR ->
                    cooling += m.thermalConductivity * 0.5;
            }
        }
        return cooling;
    }

    public static double getPressureLimit(MachineInventory inv) {
        double limit = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case CASING, PLATE, REINFORCED_PLATE, FRAME, BEAM, PANEL, BRACKET, SUPPORT ->
                        limit += m.youngsModulus * m.toughness;
            }
        }
        return limit;
    }

    public static double getPowerTransfer(MachineInventory inv) {
        double transfer = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case COIL, WIRE, CABLE, CONTACT, ELECTRODE, TERMINAL ->
                        transfer += m.electroconductivity;
            }
        }
        return transfer;
    }

    public static double getMaxTemperature(MachineInventory inv) {
        double temp = 300; // default
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case CASING, PLATE, REINFORCED_PLATE, HEAT_SINK, RADIATOR, THERMAL_LINING ->
                        temp = Math.max(temp, m.meltingPointK * 0.8);
            }
        }
        return temp;
    }

    public static double getHeatCapacity(MachineInventory inv) {
        double capacity = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case CASING, PLATE, REINFORCED_PLATE, FRAME, BEAM, HEAT_SINK, RADIATOR, COIL, HEATING_ELEMENT, THERMAL_LINING ->
                        capacity += m.specificHeat * m.density20C;
            }
        }
        return capacity;
    }

    public static double getDurability(MachineInventory inv) {
        double durability = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case CASING, PLATE, REINFORCED_PLATE, FRAME, BEAM, PANEL, BRACKET, SUPPORT,
                     SHAFT, AXLE, GEAR, SMALL_GEAR, LARGE_GEAR, BEVEL_GEAR, WORM_GEAR, CRANK, CAM, SPINDLE, BEARING, COUPLING ->
                        durability += m.toughness * m.hardness * (1 + m.ductility);
            }
        }
        return durability;
    }

    public static double getThermalExpansion(MachineInventory inv) {
        double expansion = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            expansion += m.thermalExpansion;
        }
        return expansion;
    }

    public static double getMagnetism(MachineInventory inv) {
        double magnetism = 0;
        for (MachinePart part : inv.getParts()) {
            if (part == null) continue;
            MaterialProperties m = part.material();
            switch (part.type()) {
                case COIL, MAGNET, SOLENOID -> magnetism += switch (m.magneticType) {
                    case "ferromagnetic" -> 1.0;
                    case "paramagnetic" -> 0.1;
                    case "diamagnetic" -> -0.1;
                    default -> 0;
                };
            }
        }
        return magnetism;
    }


}