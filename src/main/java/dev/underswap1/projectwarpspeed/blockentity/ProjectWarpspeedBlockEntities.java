package dev.underswap1.projectwarpspeed.blockentity;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.blocks.ProjectWarpspeedBlocks;
import dev.underswap1.projectwarpspeed.machine.machines.SteamBoilerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedBlockEntities {

    public static BlockEntityType<SteamBoilerBlockEntity> STEAM_BOILER;
    public static BlockEntityType<MachineBlockEntity> MACHINE_ENTITY;


    public static void registerAll() {
        STEAM_BOILER = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ProjectWarpspeed.MOD_ID, "steam_boiler"),
                FabricBlockEntityTypeBuilder.create(SteamBoilerBlockEntity::new, ProjectWarpspeedBlocks.STEAM_BOILER)
                        .build()
        );

        MACHINE_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(ProjectWarpspeed.MOD_ID, "machine_entity"),
                FabricBlockEntityTypeBuilder.create(MachineBlockEntity::new, ProjectWarpspeedBlocks.MACHINE_BLOCK)
                        .build()
        );
    }
}