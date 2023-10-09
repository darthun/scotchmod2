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
import net.darthun.scotchmod.recipe.MashTunRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class MashTunRecipeCategory implements IRecipeCategory<MashTunRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ScotchMod.MOD_ID, "mash_tun");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ScotchMod.MOD_ID,
            "textures/gui/mash_tun_gui.png");

    public static final RecipeType<MashTunRecipe> MASH_TUN_TYPE =
            new RecipeType<>(UID, MashTunRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MashTunRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MASH_TUN_BLOCK.get()));
    }


    @Override
    public RecipeType<MashTunRecipe> getRecipeType() {
        return MASH_TUN_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Mash Tun");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MashTunRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 28).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 24,14).addFluidStack(Fluids.WATER,1000).setFluidRenderer(1000,false,16,39);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 14).addFluidStack(ModFluids.SOURCE_WORT.get(),1000).setFluidRenderer(1000,false,16,39);
    }
}