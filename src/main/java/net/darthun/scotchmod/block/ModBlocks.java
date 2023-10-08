package net.darthun.scotchmod.block;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.custom.*;
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

    public static final RegistryObject<Block> MALTED_BARLEY_BLOCK = registerBlock("malted_barley_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    public static final RegistryObject<Block> SMOKED_MALTED_BARLEY_BLOCK = registerBlock("smoked_malted_barley_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    public static final RegistryObject<Block> BOGEARTH_BLOCK = registerBlock("bogearth_block",
            () -> new BogearthBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CHERRYOAK_BARREL_BLOCK = registerBlock("cherryoak_barrel_block",
            () -> new CherryoakBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BARLEY_STEEP_BLOCK = registerBlock("barley_steep_block",
            () -> new BarleySteepBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> MASH_TUN_BLOCK = registerBlock("mash_tun_block",
            () -> new MashTunBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> MALT_KILN_BLOCK = registerBlock("malt_kiln_block",
            () -> new MaltKilnBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> BARLEY_BLOCK = BLOCKS.register("barley_block",
            () -> new BarleyCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<LiquidBlock> WORT_BLOCK = BLOCKS.register("wort_block",
            () -> new LiquidBlock(ModFluids.SOURCE_WORT,BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<LiquidBlock> WASH_BLOCK = BLOCKS.register("wash_block",
            () -> new LiquidBlock(ModFluids.SOURCE_WASH,BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<LiquidBlock> DISTILLED_WASH_BLOCK = BLOCKS.register("distilled_wash_block",
            () -> new LiquidBlock(ModFluids.SOURCE_DISTILLED_WASH,BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<LiquidBlock> NEW_MAKE_BLOCK = BLOCKS.register("new_make_block",
            () -> new LiquidBlock(ModFluids.SOURCE_NEW_MAKE,BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<LiquidBlock> GLENDARTHUN_BLOCK = BLOCKS.register("glendarthun_block",
            () -> new LiquidBlock(ModFluids.SOURCE_GLENDARTHUN,BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<Block> CUSTOM_WATER = registerBlock("custom_water",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static final RegistryObject<Block> PAGODA_BLOCK = registerBlock("pagoda_block",
            () -> new PagodaBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()));

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
