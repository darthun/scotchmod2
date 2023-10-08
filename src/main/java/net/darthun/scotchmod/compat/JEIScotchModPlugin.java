package net.darthun.scotchmod.compat;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.recipe.BarleySteepRecipe;
import net.darthun.scotchmod.recipe.MaltKilnRecipe;
import net.darthun.scotchmod.recipe.MashTunRecipe;
import net.darthun.scotchmod.screen.BarleySteepScreen;
import net.darthun.scotchmod.screen.MaltKilnScreen;
import net.darthun.scotchmod.screen.MashTunScreen;
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

        registration.addRecipeCategories(new MaltKilnRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new MashTunRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<BarleySteepRecipe> barleySteepRecipes = recipeManager.getAllRecipesFor(BarleySteepRecipe.Type.INSTANCE);
        registration.addRecipes(BarleySteepRecipeCategory.BARLEY_STEEP_TYPE,barleySteepRecipes);

        List<MaltKilnRecipe> maltKilnRecipes = recipeManager.getAllRecipesFor(MaltKilnRecipe.Type.INSTANCE);
        registration.addRecipes(MaltKilnRecipeCategory.MALT_KILN_TYPE, maltKilnRecipes);

        List<MashTunRecipe> mashTunRecipes = recipeManager.getAllRecipesFor(MashTunRecipe.Type.INSTANCE);
        registration.addRecipes(MashTunRecipeCategory.MASH_TUN_TYPE,mashTunRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BarleySteepScreen.class,80,20,30,30,BarleySteepRecipeCategory.BARLEY_STEEP_TYPE);
        registration.addRecipeClickArea(MaltKilnScreen.class,80,20,30,30,MaltKilnRecipeCategory.MALT_KILN_TYPE);
        registration.addRecipeClickArea(MashTunScreen.class,80,20,30,30,MashTunRecipeCategory.MASH_TUN_TYPE);
    }
}
