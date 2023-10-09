package net.darthun.scotchmod.datagen;

import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHERRYOAK_BARREL_BLOCK.get())
                .pattern("CIC")
                .pattern("C C")
                .pattern("COC")
                .define('I', Items.IRON_NUGGET)
                .define('O', Blocks.OAK_SLAB)
                .define('C', Blocks.CHERRY_PLANKS)
                .unlockedBy("has_cherryplanks",inventoryTrigger(ItemPredicate.Builder.item().of(Items.CHERRY_PLANKS)
                        .build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BARLEY_STEEP_BLOCK.get())
                .pattern("CBC")
                .pattern("CWC")
                .pattern("CCC")
                .define('B', ModItems.BARLEY_GROWN.get())
                .define('W', Items.WATER_BUCKET)
                .define('C', Blocks.SMOOTH_STONE)
                .unlockedBy("has_barley",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.BARLEY_GROWN.get())
                        .build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PAGODA_BLOCK.get())
                .pattern(" F ")
                .pattern("MMM")
                .pattern("PPP")
                .define('F', Items.FURNACE)
                .define('M', ModBlocks.MALTED_BARLEY_BLOCK.get())
                .define('P', ModItems.PEAT.get())
                .unlockedBy("has_maltedbarley",inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MALTED_BARLEY_BLOCK.get())
                        .build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MASH_TUN_BLOCK.get())
                .pattern("SSS")
                .pattern("CWC")
                .pattern("CCC")
                .define('C', Items.COPPER_INGOT)
                .define('S', ModBlocks.SMOKED_MALTED_BARLEY_BLOCK.get())
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_smokedmaltedbarley",inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SMOKED_MALTED_BARLEY_BLOCK.get())
                        .build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WASHBACK_BLOCK.get())
                .pattern("YYY")
                .pattern("CWC")
                .pattern("CCC")
                .define('C', ModBlocks.CHERRYOAK_BARREL_BLOCK.get())
                .define('Y', ModItems.YEAST.get())
                .define('W', ModItems.WORT_BUCKET.get())
                .unlockedBy("has_yeast",inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.YEAST.get())
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
