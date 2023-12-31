package net.darthun.scotchmod.recipe;

import net.darthun.scotchmod.ScotchMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ScotchMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BarleySteepRecipe>> BARLEY_STEEP_SERIALIZER =
            SERIALIZERS.register("barley_steep", () -> BarleySteepRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<MaltKilnRecipe>> MALT_KILN_SERIALIZER =
            SERIALIZERS.register("malt_kiln", () -> MaltKilnRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<MashTunRecipe>> MASH_TUN_SERIALIZER =
            SERIALIZERS.register("mash_tun", () -> MashTunRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<WashbackRecipe>> WASHBACK_SERIALIZER =
            SERIALIZERS.register("washback", () -> WashbackRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
