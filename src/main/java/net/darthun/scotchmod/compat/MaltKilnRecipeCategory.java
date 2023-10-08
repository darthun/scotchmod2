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
import net.darthun.scotchmod.recipe.MaltKilnRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MaltKilnRecipeCategory implements IRecipeCategory<MaltKilnRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(ScotchMod.MOD_ID, "malt_kiln");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ScotchMod.MOD_ID,
            "textures/gui/malt_kiln_gui.png");

    public static final RecipeType<MaltKilnRecipe> MALT_KILN_TYPE =
            new RecipeType<>(UID, MaltKilnRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MaltKilnRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MALT_KILN_BLOCK.get()));
    }


    @Override
    public RecipeType<MaltKilnRecipe> getRecipeType() {
        return MALT_KILN_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Malt Kiln");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MaltKilnRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 28).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 56).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 111, 28).addItemStack(recipe.getResultItem(null));
    }
}