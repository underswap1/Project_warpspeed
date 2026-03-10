package dev.underswap1.projectwarpspeed.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.LivingEntity;

public class RadiationEffect extends StatusEffect {
    public RadiationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x98D982);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(entity.getWorld().getDamageSources().magic(), 1.0F + amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}