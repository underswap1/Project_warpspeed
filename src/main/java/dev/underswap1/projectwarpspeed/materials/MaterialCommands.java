package dev.underswap1.projectwarpspeed.materials;

import com.mojang.brigadier.arguments.StringArgumentType;

import dev.underswap1.projectwarpspeed.ProjectWarpspeed;
import dev.underswap1.projectwarpspeed.materials.blocks.BlockFamilyMap;
import dev.underswap1.projectwarpspeed.materials.blocks.BlockMaterialMap;
import dev.underswap1.projectwarpspeed.materials.items.ItemMaterialMap;
import dev.underswap1.projectwarpspeed.registry.MaterialRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MaterialCommands {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            // ---------------- MATERIAL PROPERTIES ----------------
            dispatcher.register(literal("materialproperties")
                    .then(argument("material", StringArgumentType.word())
                            .executes(context -> {
                                String materialName = StringArgumentType.getString(context, "material").toLowerCase();
                                MaterialProperties material = MaterialRegistry.get(materialName);
                                ServerCommandSource source = context.getSource();

                                if (material == null) {
                                    source.sendFeedback(() ->
                                                    Text.literal("Material not found: " + materialName).formatted(Formatting.RED),
                                            false);
                                } else {
                                    printMaterialProperties(source, material);
                                }
                                return 1;
                            })
                    )
            );

            // ---------------- BLOCK PROPERTIES ----------------
            dispatcher.register(literal("blockproperties")
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        PlayerEntity player = source.getPlayer();
                        World world = source.getWorld();

                        HitResult hitResult = Objects.requireNonNull(player).raycast(5.0, 0.0f, false);

                        if (hitResult.getType() != HitResult.Type.BLOCK) {
                            source.sendFeedback(() ->
                                    Text.literal("No block in sight.").formatted(Formatting.RED), false);
                            return 1;
                        }

                        BlockHitResult blockHit = (BlockHitResult) hitResult;
                        BlockPos blockPos = blockHit.getBlockPos();

                        MaterialProperties material = getMaterialFromBlock(world, blockPos);

                        if (material == null) {
                            source.sendFeedback(() ->
                                            Text.literal("Block has no material assigned.").formatted(Formatting.RED),
                                    false);
                        } else {
                            source.sendFeedback(() ->
                                            Text.literal("Block at " + blockPos + " uses material: " + material.name)
                                                    .formatted(Formatting.GOLD),
                                    false);
                            printMaterialProperties(source, material);
                        }
                        return 1;
                    })
            );

            // ---------------- ITEM PROPERTIES ----------------
            dispatcher.register(literal("itemproperties")
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        PlayerEntity player = source.getPlayer();

                        if (player == null) {
                            source.sendFeedback(() -> Text.literal("No player found!").formatted(Formatting.RED), false);
                            return 1;
                        }

                        ItemStack stack = player.getMainHandStack();
                        if (stack.isEmpty()) {
                            source.sendFeedback(() -> Text.literal("You are not holding any item!").formatted(Formatting.RED), false);
                            return 1;
                        }

                        MaterialProperties material = getMaterialFromItem(stack);
                        if (material == null) {
                            source.sendFeedback(() ->
                                    Text.literal("This item has no material assigned.").formatted(Formatting.RED), false);
                        } else {
                            source.sendFeedback(() ->
                                    Text.literal("Item: " + stack.getItem().getName().getString() +
                                            " uses material: " + material.name).formatted(Formatting.GOLD), false);
                            printMaterialProperties(source, material);
                        }

                        return 1;
                    })
            );

            // ---------------- MAKE ALLOY ----------------
            dispatcher.register(literal("make_alloy")
                    .then(argument("args", StringArgumentType.greedyString())
                            .executes(context -> {
                                ServerCommandSource source = context.getSource();
                                String rawArgs = StringArgumentType.getString(context, "args").toLowerCase().trim();
                                String[] tokens = rawArgs.split("\\s+");

                                if (tokens.length % 2 != 0) {
                                    source.sendFeedback(() ->
                                                    Text.literal("You must provide material-percentage pairs!").formatted(Formatting.RED),
                                            false);
                                    return 1;
                                }

                                List<String> materials = new ArrayList<>();
                                List<Double> fractions = new ArrayList<>();
                                double totalPercent = 0;

                                for (int i = 0; i < tokens.length; i += 2) {
                                    String matName = tokens[i];
                                    MaterialProperties mat = MaterialRegistry.get(matName);
                                    if (mat == null) {
                                        source.sendFeedback(() ->
                                                Text.literal("Material not found: " + matName).formatted(Formatting.RED), false);
                                        return 1;
                                    }

                                    double pct;
                                    try {
                                        pct = Double.parseDouble(tokens[i + 1]);
                                    } catch (NumberFormatException e) {
                                        int finalI = i;
                                        source.sendFeedback(() ->
                                                Text.literal("Invalid percentage: " + tokens[finalI + 1]).formatted(Formatting.RED), false);
                                        return 1;
                                    }

                                    if (pct < 0 || pct > 100) {
                                        source.sendFeedback(() ->
                                                Text.literal("Percentage must be between 0 and 100: " + pct).formatted(Formatting.RED), false);
                                        return 1;
                                    }

                                    materials.add(mat.name);
                                    fractions.add(pct / 100.0);
                                    totalPercent += pct;
                                }

                                if (Math.abs(totalPercent - 100.0) > 0.001) {
                                    double finalTotalPercent = totalPercent;
                                    source.sendFeedback(() ->
                                                    Text.literal("All percentages must sum to 100! Currently: " + finalTotalPercent).formatted(Formatting.RED),
                                            false);
                                    return 1;
                                }

                                MaterialProperties alloy = ProjectWarpspeed.ALLOY_GENERATOR.generateAlloy(materials, fractions);
                                MaterialRegistry.register(alloy);

                                source.sendFeedback(() ->
                                        Text.literal("Alloy generated: " + alloy.name).formatted(Formatting.GREEN), false);
                                printMaterialProperties(source, alloy);

                                return 1;
                            })
                    )
            );

            // ---------------- LIST ALLOYS ----------------
            dispatcher.register(literal("listalloys")
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        var alloys = ProjectWarpspeed.ALLOY_GENERATOR.getGeneratedAlloys();

                        if (alloys.isEmpty()) {
                            source.sendFeedback(() ->
                                    Text.literal("No alloys have been generated yet!").formatted(Formatting.RED), false);
                            return 1;
                        }

                        source.sendFeedback(() ->
                                Text.literal("=== Generated Alloys ===").formatted(Formatting.DARK_AQUA, Formatting.BOLD), false);

                        for (var alloyJson : alloys) {
                            String name = alloyJson.getAsJsonObject().get("name").getAsString();
                            String symbol = alloyJson.getAsJsonObject().get("symbol").getAsString();
                            source.sendFeedback(() ->
                                    Text.literal("- " + name + " (" + symbol + ")").formatted(Formatting.GOLD), false);
                        }

                        return 1;
                    })
            );

            // ---------------- ALLOY PROPERTIES ----------------
            dispatcher.register(literal("alloyproperties")
                    .then(argument("name", StringArgumentType.word())
                            .executes(context -> {
                                ServerCommandSource source = context.getSource();
                                String alloyName = StringArgumentType.getString(context, "name").toLowerCase();

                                MaterialProperties mat = MaterialRegistry.get(alloyName);
                                if (mat == null || !mat.category.equals("alloy")) {
                                    source.sendFeedback(() ->
                                            Text.literal("Alloy not found: " + alloyName).formatted(Formatting.RED), false);
                                    return 1;
                                }

                                source.sendFeedback(() ->
                                        Text.literal("=== Alloy Info ===").formatted(Formatting.DARK_AQUA, Formatting.BOLD), false);
                                printMaterialProperties(source, mat);
                                return 1;
                            })
                    )
            );
        });
    }

    // ------------------ MATERIAL / BLOCK / ITEM UTILS ------------------

    // Example function to get the material from a block
    private static MaterialProperties getMaterialFromBlock(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        String family = BlockFamilyMap.getFamily(block);
        if (family == null) return null;

        BlockMaterialMap.BlockMaterialOverride override = BlockMaterialMap.get(block);
        if (override == null) return null;

        MaterialProperties base = MaterialRegistry.get(override.baseMaterial);
        if (base == null) return null;

        return applyOverride(base, override);
    }

    private static MaterialProperties applyOverride(MaterialProperties base, BlockMaterialMap.BlockMaterialOverride override) {
        return new MaterialProperties(
                base.name,
                base.symbol,
                base.category,
                base.phase_stp,
                base.atomic_number,
                base.atomic_mass,
                override.electroconductivity != null ? override.electroconductivity : base.electroconductivity,
                override.density20C != null ? override.density20C : base.density20C,
                override.meltingPointK != null ? override.meltingPointK : base.meltingPointK,
                override.boilingPointK != null ? override.boilingPointK : base.boilingPointK,
                override.specificHeat != null ? override.specificHeat : base.specificHeat,
                override.thermalConductivity != null ? override.thermalConductivity : base.thermalConductivity,
                override.thermalExpansion != null ? override.thermalExpansion : base.thermalExpansion,
                base.magneticType,
                override.hardness != null ? override.hardness : base.hardness,
                override.youngsModulus != null ? override.youngsModulus : base.youngsModulus,
                override.poissonsRatio != null ? override.poissonsRatio : base.poissonsRatio,
                override.toughness != null ? override.toughness : base.toughness,
                override.ductility != null ? override.ductility : base.ductility
        );
    }

    private static MaterialProperties getMaterialFromItem(ItemStack stack) {
        ItemMaterialMap.ItemMaterialOverride override = ItemMaterialMap.get(stack.getItem());
        if (override == null) return null;

        MaterialProperties base =MaterialRegistry.get(override.baseMaterial);
        if (base == null) return null;

        if (base.category.equals("alloy")) {
            return MaterialRegistry.get(base.name.toLowerCase());
        }
        return base;
    }

    // ------------------ PRINT UTIL ------------------

    // Print all material properties with color formatting
    private static void printMaterialProperties(ServerCommandSource source, MaterialProperties mat) {
        source.sendFeedback(() ->
                        Text.literal("=== Material Data ===").formatted(Formatting.DARK_AQUA, Formatting.BOLD),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Name: ").formatted(Formatting.GRAY)
                                .append(Text.literal(mat.name).formatted(Formatting.GOLD)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Symbol: ").formatted(Formatting.GRAY)
                                .append(Text.literal(mat.symbol).formatted(Formatting.AQUA)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Category: ").formatted(Formatting.GRAY)
                                .append(Text.literal(mat.category).formatted(Formatting.AQUA)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Phase at STP: ").formatted(Formatting.GRAY)
                                .append(Text.literal(mat.phase_stp).formatted(Formatting.AQUA)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Atomic Number: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.atomic_number)).formatted(Formatting.LIGHT_PURPLE)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Atomic Mass: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.atomic_mass) + " g/mol").formatted(Formatting.LIGHT_PURPLE)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Electroconductivity: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.electroconductivity)).formatted(Formatting.DARK_AQUA)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Density at 20C: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.density20C) + " kg/m³").formatted(Formatting.DARK_AQUA)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Melting Point: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.meltingPointK) + " K").formatted(Formatting.RED)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Boiling Point: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.boilingPointK) + " K").formatted(Formatting.RED)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Specific Heat: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.specificHeat) + " J/(kg·K)").formatted(Formatting.YELLOW)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Thermal Conductivity: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.thermalConductivity) + " W/(m·K)").formatted(Formatting.YELLOW)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Thermal Expansion: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.thermalExpansion) + " 1/K").formatted(Formatting.YELLOW)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("magnetic Type: ").formatted(Formatting.GRAY)
                                .append(Text.literal(mat.magneticType).formatted(Formatting.BLUE)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Hardness: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.hardness)).formatted(Formatting.GREEN)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Young's Modulus: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.youngsModulus) + " GPa").formatted(Formatting.GREEN)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Poisson's Ratio: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.poissonsRatio)).formatted(Formatting.GREEN)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Toughness: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.toughness)).formatted(Formatting.DARK_PURPLE)),
                false
        );

        source.sendFeedback(() ->
                        Text.literal("Ductility: ").formatted(Formatting.GRAY)
                                .append(Text.literal(formatNumber(mat.ductility)).formatted(Formatting.DARK_PURPLE)),
                false
        );
    }

    private static String formatNumber(double value) {
        if (Math.abs(value) < 0.001) {          // very small numbers
            return String.format("%.3E", value).replaceAll("\\.?0+$", "");
        } else if (Math.abs(value) > 1000000) {  // very big numbers
            return String.format("%.3E", value).replaceAll("\\.?0+$", "");
        } else {
            return String.format("%.3E", value).replaceAll("\\.?0+$", "");
        }
    }
}