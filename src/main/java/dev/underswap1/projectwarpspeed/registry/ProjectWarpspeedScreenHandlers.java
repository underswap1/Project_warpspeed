package dev.underswap1.projectwarpspeed.registry;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.screen.MachineScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedScreenHandlers {

    public static ScreenHandlerType<MachineScreenHandler> MACHINE;

    public static void registerAll() {
        MACHINE = Registry.register(
                Registries.SCREEN_HANDLER,
                new Identifier(ProjectWarpspeed.MOD_ID, "machine"),
                new ScreenHandlerType<>(MachineScreenHandler::new, null)
        );
    }
}
