package dev.underswap1.projectwarpspeed.effects;

import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RadiationEffect extends StatusEffect {
    public RadiationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x98D982);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(ProjectWarpspeedDamageTypes.radiation(entity), 1.0F + amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}