package dev.underswap1.projectwarpspeed.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import dev.underswap1.projectwarpspeed.ProjectWarpspeed;

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

    public static class RadiationEffect extends StatusEffect {
        public RadiationEffect() {
            super(StatusEffectCategory.HARMFUL, 0x98D982);
        }

        @Override
        public void applyUpdateEffect(net.minecraft.entity.LivingEntity entity, int amplifier) {
            entity.damage(entity.getWorld().getDamageSources().magic(), 1.0F + amplifier);
        }

        @Override
        public boolean canApplyUpdateEffect(int duration, int amplifier) {
            return true;
        }
    }
}