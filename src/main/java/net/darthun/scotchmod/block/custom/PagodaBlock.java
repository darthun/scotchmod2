package net.darthun.scotchmod.block.custom;

import net.darthun.scotchmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.scotchmod.pagoda_block"));

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
