package net.darthun.scotchmod.datagen.loot;

import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    // every block requires .nodrops() otherwise will crash
    @Override
    protected void generate() {
//        this.dropSelf(ModBlocks.ALEXANDRITE_BLOCK.get());

//        this.add(ModBlocks.ALEXANDRITE_ORE.get(),
//                block -> createDrop(ModBlocks.ALEXANDRITE_ORE().get(), ModItems.RAW_ALEXANDRITE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
