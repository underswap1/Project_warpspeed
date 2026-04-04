package dev.underswap1.projectwarpspeed.materials.core;

public class MaterialEngineering {

    public static double getPressureLimit(MaterialProperties m) {
        double safetyFactor = 0.5;
        return m.toughness * 1e6 * m.youngsModulus * 1e9 * safetyFactor; // J/m² * GPa -> Pa
    }

    public static double getHeatLimit(MaterialProperties m) {
        double safetyFactor = 0.65;
        return m.meltingPointK * safetyFactor;
    }

    public static double getThermalStress(MaterialProperties m, double deltaT) {
        return m.youngsModulus * m.thermalExpansion * deltaT; // Thermal stress = E * α * ΔT
    }

    public static double getElectricalResistance(MaterialProperties m, double lengthMeters, double areaM2) {
        return lengthMeters / (m.electroconductivity * 1e6 * areaM2); // S/m -> Ohm
    }

    public static double getHeatTransfer(MaterialProperties m, double areaM2, double deltaT, double thicknessM) {
        return m.thermalConductivity * areaM2 * deltaT / thicknessM;
    }

    public static double getWearResistance(MaterialProperties m) {
        return m.hardness * m.toughness;
    }

    public static double getShockResistance(MaterialProperties m) {
        return m.toughness * m.ductility;
    }

    public static boolean checkFailure(MaterialProperties m, double appliedStress, double temperature) {
        if (appliedStress > getPressureLimit(m)) return true;
        return temperature > getHeatLimit(m);
    }

    public static double maxStructuralStress(MaterialProperties m, double deltaT) {
        double thermal = getThermalStress(m, deltaT);
        double pressure = getPressureLimit(m);
        return Math.min(thermal, pressure);
    }
}
