package dev.underswap1.projectwarpspeed.machine.machines;

import dev.underswap1.projectwarpspeed.blockentity.ProjectWarpspeedBlockEntities;
import dev.underswap1.projectwarpspeed.machine.*;
import dev.underswap1.projectwarpspeed.materials.core.MaterialProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SteamBoilerBlockEntity extends BlockEntity {

    private final MachineInventory inventory = new MachineInventory();
    private final List<InstalledPart> installedParts = new ArrayList<>();

    public SteamBoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ProjectWarpspeedBlockEntities.STEAM_BOILER, pos, state);
        setupDefaultPartsFromRecipe();
    }

    public void setupDefaultPartsFromRecipe() {
        inventory.clear();
        installedParts.clear();

        for (Map.Entry<MachinePartType, Integer> entry : SteamBoilerRecipe.REQUIRED.entrySet()) {
            addPart(entry.getKey(), entry.getValue());
        }
    }


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