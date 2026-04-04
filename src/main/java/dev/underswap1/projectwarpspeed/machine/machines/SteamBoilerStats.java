package dev.underswap1.projectwarpspeed.machine.machines;

public class SteamBoilerStats {
    public double pressureLimit;
    public double heatCapacity;
    public double maxTemperature;
    public double cooling;
    public double powerTransfer;
    public double durability;

    @Override
    public String toString() {
        return "SteamBoilerStats{" +
                "pressureLimit=" + pressureLimit +
                ", heatCapacity=" + heatCapacity +
                ", maxTemperature=" + maxTemperature +
                ", cooling=" + cooling +
                ", powerTransfer=" + powerTransfer +
                ", durability=" + durability +
                '}';
    }
}

