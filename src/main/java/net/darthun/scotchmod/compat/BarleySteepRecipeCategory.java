package net.darthun.scotchmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.recipe.BarleySteepRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
/*
public class BarleySteepRecipeCategory implements IRecipeCategory<BarleySteepRecipe> {
    public static final ResourceLocation UID= new ResourceLocation(ScotchMod.MOD_ID,"barley_steep");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ScotchMod.MOD_ID,
            "textures/gui/barley_steep_gui.png");

    public final RecipeType BARLEY_STEEP_RECIPE =
            new RecipeType(UID,);

    private final IDrawable background;
    private final IDrawable icon;

    public BarleySteepRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ModBlocks.BARLEY_STEEP_BLOCK.get()));
    }

    @Override
    public RecipeType getRecipeType() {
        return null;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Barley Steep");
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
    public void setRecipe(IRecipeLayoutBuilder builder, Object recipe, IFocusGroup focuses) {

    }
}
*/