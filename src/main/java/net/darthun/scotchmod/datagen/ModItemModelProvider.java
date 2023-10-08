package net.darthun.scotchmod.datagen;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.datagen.loot.ModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ScotchMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //simpleItem(ModItems.ALEXANDRITE);
        complexBlock(ModBlocks.CHERRYOAK_BARREL_BLOCK.get());
        complexBlock(ModBlocks.BARLEY_STEEP_BLOCK.get());
        complexBlock(ModBlocks.PAGODA_BLOCK.get());
        complexBlock(ModBlocks.MALT_KILN_BLOCK.get());

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ScotchMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder complexBlock(Block block){
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation(ScotchMod.MOD_ID,
                "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
    }
}
