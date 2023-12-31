package net.darthun.scotchmod.datagen;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.block.custom.BarleyCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ScotchMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //blockWithItem(ModBlocks.ALEXANDRITE_BLOCK);
        makeCrop(((BarleyCropBlock)ModBlocks.BARLEY_BLOCK.get()),"barley_stage","barley_stage");
        horizontalBlock(ModBlocks.CHERRYOAK_BARREL_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/cherryoak_barrel_block")));
        horizontalBlock(ModBlocks.BARLEY_STEEP_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/barley_steep_block")));
        blockWithItem(ModBlocks.MALTED_BARLEY_BLOCK);
        blockWithItem(ModBlocks.SMOKED_MALTED_BARLEY_BLOCK);
        simpleBlock(ModBlocks.PAGODA_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/pagoda_block")));
        horizontalBlock(ModBlocks.MALT_KILN_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/malt_kiln_block")));
        horizontalBlock(ModBlocks.MASH_TUN_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/mash_tun_block")));
        horizontalBlock(ModBlocks.WASHBACK_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/washback_block")));

    }

    //Normal block like IRON
    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }

    //Crops
    public void makeCrop(CropBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> states(state,block,modelName,textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName,String textureName){
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0]= new ConfiguredModel(models().crop(modelName+state.getValue(((BarleyCropBlock) block).getAgeProperty()),
                new ResourceLocation(ScotchMod.MOD_ID,"block/"+textureName+state.getValue(((BarleyCropBlock) block).getAgeProperty())))
                .renderType("cutout"));
        return models;
    }

}
