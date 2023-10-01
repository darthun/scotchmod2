package net.darthun.scotchmod.compat;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.recipe.BarleySteepRecipe;
import net.darthun.scotchmod.screen.BarleySteepScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIScotchModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ScotchMod.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BarleySteepRecipeCategory(
                        registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<BarleySteepRecipe> barleySteepRecipes = recipeManager.getAllRecipesFor(BarleySteepRecipe.Type.INSTANCE);
        registration.addRecipes(BarleySteepRecipeCategory.BARLEY_STEEP_TYPE,barleySteepRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BarleySteepScreen.class,80,20,30,30,BarleySteepRecipeCategory.BARLEY_STEEP_TYPE);
    }
}
