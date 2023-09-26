package net.darthun.scotchmod.datagen;

import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.lwjgl.openal.AL;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    //private static final List<ItemLike> ALEXANDRITE_SMELTABLES= List.of(ModItems.RAW_ALEX.get(),ModBlocks.ALEX_ORE.get()));
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PEAT_BLOCK.get())
                .pattern("BB ")
                .pattern("BB ")
                .pattern("   ")
                .define('B', ModItems.PEAT.get())
                .unlockedBy("has_peat",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.PEAT.get())
                        .build()))
                .save(pWriter);

//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.GLENDARTHUN_BUCKET.get())
//                .requires(ModItems.BARLEY_GROWN.get(),9)
//                .unlockedBy("has_glendarthun",inventoryTrigger(ItemPredicate.Builder.item()
//                        .of(ModItems.GLENDARTHUN.get())
//                        .build()))
//                .save(pWriter);

//        nineBlockStorageRecipes(pWriter,RecipeCategory.MISC,ModItems.PEAT.get(),
//                RecipeCategory.MISC,ModBlocks.PEAT_BLOCK.get());

        //oreSmelting(pWriter,ALEXANDRITE_SMELTABLES,RecipeCategory.MISC,ModItems.ALEX.get(),0.25f,200,"alexandrite");
        //oreBlasting(pWriter,ALEXANDRITE_SMELTABLES,RecipeCategory.MISC,ModItems.ALEX.get(),0.25f,200,"alexandrite");
    }
}
