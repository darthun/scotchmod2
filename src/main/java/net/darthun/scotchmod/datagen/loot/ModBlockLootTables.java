package net.darthun.scotchmod.datagen.loot;

import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.block.custom.BarleyCropBlock;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.fml.common.Mod;
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
        this.dropSelf(ModBlocks.CHERRYOAK_BARREL_BLOCK.get());
        this.dropOther(ModBlocks.PEAT_BLOCK.get(),ModItems.PEAT.get());
        this.dropSelf(ModBlocks.BARLEY_STEEP_BLOCK.get());
        this.dropSelf(ModBlocks.MALTED_BARLEY_BLOCK.get());
        this.dropSelf(ModBlocks.SMOKED_MALTED_BARLEY_BLOCK.get());
        this.dropSelf(ModBlocks.PAGODA_BLOCK.get());
        this.dropSelf(ModBlocks.MALT_KILN_BLOCK.get());
        this.dropSelf(ModBlocks.MASH_TUN_BLOCK.get());


        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BARLEY_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BarleyCropBlock.AGE, 7));
        this.add(ModBlocks.BARLEY_BLOCK.get(), createCropDrops(ModBlocks.BARLEY_BLOCK.get(),ModItems.BARLEY_GROWN.get(),
                ModItems.BARLEY_SEED.get(),lootitemcondition$builder1 ));



//        this.dropSelf(ModBlocks.ALEXANDRITE_BLOCK.get());
//       this.add(ModBlocks.ALEXANDRITE_ORE.get(),
//                block -> createDrop(ModBlocks.ALEXANDRITE_ORE().get(), ModItems.RAW_ALEXANDRITE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
