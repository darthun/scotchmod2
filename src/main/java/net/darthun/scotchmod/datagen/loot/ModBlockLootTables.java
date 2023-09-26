package net.darthun.scotchmod.datagen.loot;

import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.block.custom.BarleyCropBlock;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    // every block requires .nodrops() otherwise will crash
    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.BOGEARTH_BLOCK.get());
        this.dropOther(ModBlocks.PEAT_BLOCK.get(),ModItems.PEAT.get());


        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BARLEY_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BarleyCropBlock.AGE, 7));
        this.createCropDrops(ModBlocks.BARLEY_BLOCK.get(),ModItems.BARLEY_GROWN.get(),ModItems.BARLEY_SEED.get(),lootitemcondition$builder1 );

//        this.dropSelf(ModBlocks.ALEXANDRITE_BLOCK.get());

//        this.add(ModBlocks.ALEXANDRITE_ORE.get(),
//                block -> createDrop(ModBlocks.ALEXANDRITE_ORE().get(), ModItems.RAW_ALEXANDRITE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
