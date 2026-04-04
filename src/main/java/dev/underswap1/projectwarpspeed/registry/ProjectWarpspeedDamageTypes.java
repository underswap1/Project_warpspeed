package dev.underswap1.projectwarpspeed.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedDamageTypes {
    public static final RegistryKey<DamageType> RADIATION =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("project_warpspeed", "radiation"));

    public static DamageSource radiation(LivingEntity entity) {
        return new DamageSource(
                entity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(RADIATION)
        );
    }

    public static DamageSource radiation(LivingEntity entity, LivingEntity attacker) {
        return new DamageSource(
                entity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(RADIATION),
                attacker
        );
    }
}

