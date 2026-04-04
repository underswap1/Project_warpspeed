package dev.underswap1.projectwarpspeed.machine.machines;

import dev.underswap1.projectwarpspeed.blockentity.ProjectWarpspeedBlockEntities;
import dev.underswap1.projectwarpspeed.machine.*;
import dev.underswap1.projectwarpspeed.materials.core.MaterialProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SteamBoilerBlockEntity extends BlockEntity {

    private final MachineInventory inventory = new MachineInventory();
    private final List<InstalledPart> installedParts = new ArrayList<>();

    public SteamBoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ProjectWarpspeedBlockEntities.STEAM_BOILER, pos, state);
        setupDefaultParts();
    }

    /** Initialize with 36 realistic boiler parts */
    private void setupDefaultParts() {
        // Structural (12)
        addPart(MachinePartType.PLATE, 8);
        addPart(MachinePartType.REINFORCED_PLATE, 2);
        addPart(MachinePartType.SUPPORT, 2);

        // Fasteners (8)
        addPart(MachinePartType.BOLT, 4);
        addPart(MachinePartType.RIVET, 2);
        addPart(MachinePartType.BRACKET, 2);

        // Pressure / Fluid Core (8)
        addPart(MachinePartType.PIPE, 3);
        addPart(MachinePartType.VALVE, 2);
        addPart(MachinePartType.CYLINDER, 1);
        addPart(MachinePartType.PISTON, 1);
        addPart(MachinePartType.MANIFOLD, 1);

        // Thermal System (5)
        addPart(MachinePartType.COIL, 2);
        addPart(MachinePartType.HEATING_ELEMENT, 1);
        addPart(MachinePartType.BURNER, 1);
        addPart(MachinePartType.THERMAL_LINING, 1);
    }

    /** Helper to add multiple copies of the same part type */
    private void addPart(MachinePartType type, int count) {
        for (int i = 0; i < count; i++) {
            MachinePart part = new MachinePart(type, MaterialProperties.DEFAULT_METAL);
            inventory.setPart(part);
            installedParts.add(new InstalledPart(part));
        }
    }

    public MachineInventory getInventory() {
        return inventory;
    }

    public List<InstalledPart> getInstalledParts() {
        return installedParts;
    }

    /** Calculate real-time stats from MachineInventory */
    public SteamBoilerStats calculateStats() {
        SteamBoilerStats stats = new SteamBoilerStats();
        stats.pressureLimit = MachineStats.getPressureLimit(inventory);
        stats.heatCapacity = MachineStats.getHeatCapacity(inventory);
        stats.maxTemperature = MachineStats.getMaxTemperature(inventory);
        stats.cooling = MachineStats.getCooling(inventory);
        stats.powerTransfer = MachineStats.getPowerTransfer(inventory);
        stats.durability = MachineStats.getDurability(inventory);
        return stats;
    }
}