package dev.underswap1.projectwarpspeed.blocks;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedBlocks {

    public static Block MACHINE_BLOCK;
    public static Block STEAM_BOILER;

    public static void registerAll() {
        MACHINE_BLOCK = Registry.register(
                Registries.BLOCK,
                new Identifier(ProjectWarpspeed.MOD_ID, "machine_block"),
                new Block(FabricBlockSettings.create()
                        .strength(4.0f)
                        .requiresTool()
                        .sounds(BlockSoundGroup.METAL)
                )
        );
        STEAM_BOILER = Registry.register(
                Registries.BLOCK,
                new Identifier(ProjectWarpspeed.MOD_ID, "steam_boiler"),
                new Block(FabricBlockSettings.create()
                        .strength(4.0f)
                        .requiresTool()
                        .sounds(BlockSoundGroup.METAL)
                )
        );
    }
}