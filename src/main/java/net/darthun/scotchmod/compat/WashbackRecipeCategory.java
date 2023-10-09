package net.darthun.scotchmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.fluid.ModFluids;
import net.darthun.scotchmod.recipe.WashbackRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class WashbackRecipeCategory implements IRecipeCategory<WashbackRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ScotchMod.MOD_ID, "washback");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ScotchMod.MOD_ID,
            "textures/gui/mash_tun_gui.png");

    public static final RecipeType<WashbackRecipe> WASHBACK_TYPE =
            new RecipeType<>(UID, WashbackRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public WashbackRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WASHBACK_BLOCK.get()));
    }


    @Override
    public RecipeType<WashbackRecipe> getRecipeType() {
        return WASHBACK_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Washback");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WashbackRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 28).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 24,14).addFluidStack(ModFluids.SOURCE_WORT.get(),1000).setFluidRenderer(1000,false,16,39);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 14).addFluidStack(ModFluids.SOURCE_WASH.get(),1000).setFluidRenderer(1000,false,16,39);
    }
}