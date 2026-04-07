package dev.underswap1.projectwarpspeed.blocks;

import dev.underswap1.projectwarpspeed.blockentity.MachineBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineBlock extends BlockWithEntity {

    public MachineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MachineBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof MachineBlockEntity machine) {
                player.openHandledScreen((NamedScreenHandlerFactory) machine);
            }
        }

        return ActionResult.SUCCESS;
    }

    // uncomment only if we need ticking
    //@Override
    //public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
    //        net.minecraft.world.World world,
    //        BlockState state,
    //        BlockEntityType<T> type
    //) {
    //    return world.isClient ? null : (world1, pos, state1, be) -> {
    //        if (be instanceof MachineBlockEntity machine) {
    //            machine.tick(world1, pos, state1, machine);
    //        }
    //    };
    //}
}

