package dev.underswap1.projectwarpspeed.registry;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.blocks.ProjectWarpspeedBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ProjectWarpspeedItemGroups {

    public static final RegistryKey<ItemGroup> PARTS_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ProjectWarpspeed.MOD_ID, "parts"));

    public static final RegistryKey<ItemGroup> MACHINES_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ProjectWarpspeed.MOD_ID, "machines"));

    public static void registerAll() {

        Registry.register(Registries.ITEM_GROUP, PARTS_GROUP,
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.project_warpspeed.parts"))
                        .icon(() -> {
                            // Use first generated item as icon
                            var first = MaterialRegistry.getAllParts().values().stream().findFirst();
                            return new ItemStack(first.orElseThrow());
                        })
                        .entries((context, entries) -> {
                            // Add ALL auto‑generated items
                            MaterialRegistry.getAllParts().values().forEach(entries::add);
                        })
                        .build()
        );

        Registry.register(Registries.ITEM_GROUP, MACHINES_GROUP,
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.project_warpspeed.machines"))
                        .icon(() -> new ItemStack(ProjectWarpspeedBlocks.STEAM_BOILER)) // icon for the tab
                        .entries((ctx, entries) -> {
                            entries.add(ProjectWarpspeedBlocks.MACHINE_BLOCK);
                            entries.add(ProjectWarpspeedBlocks.STEAM_BOILER);

                            // Add more machines here as you create them
                        })
                        .build()
        );
    }
}
