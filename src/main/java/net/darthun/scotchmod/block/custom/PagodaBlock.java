package net.darthun.scotchmod.block.custom;

import net.darthun.scotchmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PagodaBlock extends Block {
    public PagodaBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {

        BlockState below = pLevel.getBlockState(pPos.below());
        if(below.getBlock() == Blocks.FURNACE && !pLevel.isClientSide){
            pLevel.setBlock(pPos.below(), ModBlocks.MALT_KILN_BLOCK.get().defaultBlockState(), 3);
        }

        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }
}
