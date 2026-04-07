package dev.underswap1.projectwarpspeed.client;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedScreenHandlers;
import dev.underswap1.projectwarpspeed.screen.MachineScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ProjectWarpspeedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ProjectWarpspeed.LOGGER.info("Project Warpspeed client initialized!");
        // Register sky renderer or client-only features here
        HandledScreens.register(
                ProjectWarpspeedScreenHandlers.MACHINE,
                MachineScreen::new
        );
    }


}