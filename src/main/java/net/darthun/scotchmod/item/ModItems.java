package net.darthun.scotchmod.item;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.fluid.ModFluids;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
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
            ()-> new ItemNameBlockItem(ModBlocks.BARLEY_BLOCK.get(),new Item.Properties()));
    public static final RegistryObject<Item> BARLEY_GROWN = ITEMS.register("barleygrown",
            ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLENDARTHUN = ITEMS.register("glendarthun",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YEAST = ITEMS.register("yeast",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WORT_BUCKET = ITEMS.register("wort_bucket",
            ()-> new BucketItem(ModFluids.SOURCE_WORT,new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
