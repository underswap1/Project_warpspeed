package dev.underswap1.projectwarpspeed;

import dev.underswap1.projectwarpspeed.materials.MaterialCommands;
import dev.underswap1.projectwarpspeed.registry.MaterialRegistry;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedBiomes;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedEffects;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectWarpspeed implements ModInitializer {
    public static final String MOD_ID = "project_warpspeed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Project Warpspeed initialized by underswap1!");
        ProjectWarpspeedEffects.registerALL();
        ProjectWarpspeedBiomes.registerAll();
        ProjectWarpspeedBiomes.createBiomes();
        MaterialRegistry.loadMaterials();
        MaterialCommands.registerCommands();
    }
}