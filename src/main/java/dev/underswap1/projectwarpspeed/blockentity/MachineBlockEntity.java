package dev.underswap1.projectwarpspeed.blockentity;

import dev.underswap1.projectwarpspeed.machine.MachineInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineBlockEntity extends BlockEntity implements BlockEntityTicker<MachineBlockEntity> {
    @Override
    public void tick(World world, BlockPos pos, BlockState state, MachineBlockEntity blockEntity) {
    }
    private final MachineInventory inventory = new MachineInventory();

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        super(ProjectWarpspeedBlockEntities.MACHINE_ENTITY, pos, state);
    }

    public MachineInventory getMachineInventory() {
        return inventory;
    }
}