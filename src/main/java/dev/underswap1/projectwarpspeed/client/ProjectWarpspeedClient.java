package dev.underswap1.projectwarpspeed.client;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import net.fabricmc.api.ClientModInitializer;

public class ProjectWarpspeedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ProjectWarpspeed.LOGGER.info("Project Warpspeed client initialized!");
        // Register sky renderer or client-only features here later
    }
}