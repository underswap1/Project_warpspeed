package dev.underswap1.projectwarpspeed.machine;

public class InstalledPart {
    private final MachinePart part;
    private double wear = 0.0; // optional wear/damage

    public InstalledPart(MachinePart part) {
        this.part = part;
    }

    public MachinePart getPart() {
        return part;
    }

    public double getWear() {
        return wear;
    }

    public void applyWear(double amount) {
        wear = Math.min(1.0, wear + amount);
    }
}
