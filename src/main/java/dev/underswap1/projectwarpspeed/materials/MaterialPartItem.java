package dev.underswap1.projectwarpspeed.materials;

import dev.underswap1.projectwarpspeed.machine.MachinePart;
import dev.underswap1.projectwarpspeed.machine.MachinePartType;
import dev.underswap1.projectwarpspeed.materials.core.MaterialProperties;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MaterialPartItem extends Item {
    private final MaterialProperties material;
    private final MachinePartType partType;

    public MaterialPartItem(MaterialProperties material, MachinePartType partType) {
        super(new Item.Settings().maxCount(64));
        this.material = material;
        this.partType = partType;
    }

    public MachinePart toPart() {
        return new MachinePart(partType, material);
    }

    public MaterialProperties getMaterial() {
        return material;
    }

    public MachinePartType getPartType() {
        return partType;
    }

    @Override
    public Text getName(ItemStack stack) {
        String matName = material.name.substring(0, 1).toUpperCase() + material.name.substring(1);
        String partName = partType.name().replace("_", " ");
        partName = partName.charAt(0) + partName.substring(1).toLowerCase();
        return Text.literal(matName + " " + partName);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Material: §f" + material.name));
        tooltip.add(Text.literal("§7Part: §f" + partType.name().replace("_", " ")));
    }
}
