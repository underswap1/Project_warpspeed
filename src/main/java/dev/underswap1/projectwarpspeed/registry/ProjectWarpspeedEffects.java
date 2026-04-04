package dev.underswap1.projectwarpspeed.registry;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.effects.RadiationEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedEffects {
    public static StatusEffect RADIATION;

    public static void registerALL() {
        RADIATION = Registry.register(
                Registries.STATUS_EFFECT,
                new Identifier(ProjectWarpspeed.MOD_ID, "radiation"),
                new RadiationEffect()
        );
        ProjectWarpspeed.LOGGER.info("Registered custom effect: radiation");
    }
}