package net.darthun.scotchmod.block;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.custom.BarleyCropBlock;
import net.darthun.scotchmod.block.custom.BogearthBlock;
import net.darthun.scotchmod.fluid.ModFluids;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ScotchMod.MOD_ID);

    public static final RegistryObject<Block> PEAT_BLOCK = registerBlock("peat_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BOGEARTH_BLOCK = registerBlock("bogearth_block",
            () -> new BogearthBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BARLEY_BLOCK = BLOCKS.register("barley_block",
            () -> new BarleyCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<LiquidBlock> WORT_BLOCK = BLOCKS.register("wort_block",
            () -> new LiquidBlock(ModFluids.SOURCE_WORT,BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name,()-> new BlockItem(block.get(),new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
