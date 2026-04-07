package dev.underswap1.projectwarpspeed.blockentity;

import dev.underswap1.projectwarpspeed.blocks.ProjectWarpspeedBlocks;
import dev.underswap1.projectwarpspeed.machine.MachineInventory;
import dev.underswap1.projectwarpspeed.machine.MachinePartType;
import dev.underswap1.projectwarpspeed.machine.machines.SteamBoilerBlockEntity;
import dev.underswap1.projectwarpspeed.machine.machines.SteamBoilerRecipe;
import dev.underswap1.projectwarpspeed.materials.MaterialPartItem;
import dev.underswap1.projectwarpspeed.screen.MachineScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class MachineBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private final MachineInventory machineInventory = new MachineInventory();

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        super(ProjectWarpspeedBlockEntities.MACHINE_ENTITY, pos, state);
    }

    public MachineInventory getMachineInventory() {
        return machineInventory;
    }

    public Text getDisplayName() {
        return Text.literal("Machine");
    }

    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MachineScreenHandler(syncId, playerInventory, this);
    }

    public boolean tryAssembleSteamBoiler(PlayerEntity player) {
        if (world == null || world.isClient) return false;
        if (!hasAllRequiredParts(player.getInventory())) {
            return false;
        }

        consumeRequiredParts(player.getInventory());
        transformIntoSteamBoiler();
        return true;
    }

    private boolean hasAllRequiredParts(Inventory inv) {
        for (Map.Entry<MachinePartType, Integer> entry : SteamBoilerRecipe.REQUIRED.entrySet()) {
            MachinePartType type = entry.getKey();
            int needed = entry.getValue();
            int found = 0;

            for (int i = 0; i < inv.size(); i++) {
                ItemStack stack = inv.getStack(i);
                if (stack.getItem() instanceof MaterialPartItem mpi && mpi.getPartType() == type) {
                    found += stack.getCount();
                    if (found >= needed) break;
                }
            }

            if (found < needed) return false;
        }
        System.out.print("i did somthing");
        return true;
    }

    private void consumeRequiredParts(Inventory inv) {
        for (Map.Entry<MachinePartType, Integer> entry : SteamBoilerRecipe.REQUIRED.entrySet()) {
            MachinePartType type = entry.getKey();
            int remaining = entry.getValue();

            for (int i = 0; i < inv.size() && remaining > 0; i++) {
                ItemStack stack = inv.getStack(i);
                if (stack.getItem() instanceof MaterialPartItem mpi && mpi.getPartType() == type) {
                    int take =Math.min(stack.getCount(), remaining);
                    stack.decrement(take);
                    remaining -= take;
                }
            }
        }
    }

    private void transformIntoSteamBoiler() {
        if (world == null) return;

        BlockState newState = ProjectWarpspeedBlocks.STEAM_BOILER.getDefaultState();
        world.setBlockState(pos, newState, 3);

        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof SteamBoilerBlockEntity boiler) {
            boiler.setupDefaultPartsFromRecipe();
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        // ToDo: save machineInventory if needed
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        // ToDo: load machineInventory if needed
    }
}