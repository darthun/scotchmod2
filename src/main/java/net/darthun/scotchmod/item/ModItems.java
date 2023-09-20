package net.darthun.scotchmod.item;

import net.darthun.scotchmod.ScotchMod;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ScotchMod.MOD_ID);

    public static final RegistryObject<Item> PEAT = ITEMS.register("peat",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARLEY_SEED = ITEMS.register("barleyseed",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BARLEY_GROWN = ITEMS.register("barleygrown",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLENDARTHUN = ITEMS.register("glendarthun",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YEAST = ITEMS.register("yeast",
            ()-> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
