package net.darthun.scotchmod.block.custom;

import com.mojang.logging.LogUtils;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class BogearthBlock extends Block {
    public static IntegerProperty MOISTURE = IntegerProperty.create("moisture",0,7);
    public BogearthBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(MOISTURE,0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(MOISTURE);
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.scotchmod.bogearth_block"));

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {

        int i = pState.getValue(MOISTURE);
        if (isNearWater(pLevel, pPos) || pLevel.isRainingAt(pPos.above())) {
            if (i < 7) {
                pLevel.setBlock(pPos, pState.setValue(MOISTURE, i + 1), 3);
            }
            else {
                pLevel.setBlock(pPos, ModBlocks.PEAT_BLOCK.get().defaultBlockState(), 3);
            }
        }
    }

    private boolean isNearWater(ServerLevel pLevel, BlockPos pPos) {
        BlockState state = pLevel.getBlockState(pPos);
        for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
            FluidState fState = pLevel.getFluidState(blockpos);
            if (fState.is(Fluids.WATER) || fState.is(Fluids.FLOWING_WATER)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    private boolean isPeatyTest(BlockState blockState){
        return blockState.is(ModTags.Blocks.PEATY_BLOCKS);
    }
}
