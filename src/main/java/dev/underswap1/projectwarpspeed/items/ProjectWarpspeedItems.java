package dev.underswap1.projectwarpspeed.items;

import dev.underswap1.projectwarpspeed.blocks.ProjectWarpspeedBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedItems {
    public static Item STEAM_BOILER_ITEM;

    public static void registerAll() {
        STEAM_BOILER_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier("project_warpspeed", "steam_boiler"),
                new BlockItem(ProjectWarpspeedBlocks.STEAM_BOILER, new FabricItemSettings())
        );

    }
}
