package dev.underswap1.projectwarpspeed.screen;

import dev.underswap1.projectwarpspeed.blockentity.MachineBlockEntity;
import dev.underswap1.projectwarpspeed.registry.ProjectWarpspeedScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class MachineScreenHandler extends ScreenHandler {

    private final MachineBlockEntity machine;
    private final World world;
    private final PlayerEntity player;

    // This constructor is used by the ScreenHandlerType factory
    public MachineScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, null);
    }

    // This constructor is used when opening the block
    public MachineScreenHandler(int syncId, PlayerInventory playerInventory, MachineBlockEntity machine) {
        super(ProjectWarpspeedScreenHandlers.MACHINE, syncId);
        this.machine = machine;
        this.world = playerInventory.player.getWorld();
        this.player = playerInventory.player;

        // Add player inventory slots
        int m;
        for (m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        // Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    // Required method
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Called by the UI button
    public void assembleSteamBoiler() {
        if (machine != null && !world.isClient) {
            machine.tryAssembleSteamBoiler(player);
        }
    }
}
