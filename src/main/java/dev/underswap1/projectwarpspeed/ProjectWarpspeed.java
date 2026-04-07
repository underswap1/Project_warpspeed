package dev.underswap1.projectwarpspeed;

import dev.underswap1.projectwarpspeed.blockentity.ProjectWarpspeedBlockEntities;
import dev.underswap1.projectwarpspeed.blocks.ProjectWarpspeedBlocks;
import dev.underswap1.projectwarpspeed.materials.core.AlloyGenerator;
import dev.underswap1.projectwarpspeed.materials.core.MaterialCommands;
import dev.underswap1.projectwarpspeed.registry.MaterialRegistry;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedEffects;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedItemGroups;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectWarpspeed implements ModInitializer {
    public static final String MOD_ID = "project_warpspeed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static AlloyGenerator ALLOY_GENERATOR;

    @Override
    public void onInitialize() {
        LOGGER.info("Project Warpspeed initialized by underswap1!");
        ProjectWarpspeedEffects.registerALL();
        ProjectWarpspeedBlocks.registerAll();
        dev.underswap1.projectwarpspeed.items.ProjectWarpspeedItems.registerAll();
        ProjectWarpspeedBlockEntities.registerAll();
        ProjectWarpspeedItemGroups.registerAll();
        ProjectWarpspeedScreenHandlers.registerAll();
        MaterialRegistry.loadMaterials();
        MaterialRegistry.registerAllItems();
        ALLOY_GENERATOR = new AlloyGenerator();
        MaterialCommands.registerCommands();
    }
}