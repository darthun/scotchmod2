package net.darthun.scotchmod.item;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ScotchMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SCOTCHMOD_TAB = CREATIVE_MOD_TABS.register("scotchmod_tab",
            ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.GLENDARTHUN.get()))
            .title(Component.translatable("creativetab.scotchmod_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PEAT.get());
                        pOutput.accept(ModItems.GLENDARTHUN.get());
                        pOutput.accept(Items.DIAMOND);
                        pOutput.accept(ModBlocks.PEAT_BLOCK.get());
                        pOutput.accept(ModBlocks.BOGEARTH_BLOCK.get());
                        pOutput.accept(ModItems.BARLEY_SEED.get());
                        pOutput.accept(ModItems.BARLEY_GROWN.get());
                        pOutput.accept(ModItems.YEAST.get());
                        pOutput.accept(ModItems.WORT_BUCKET.get());
                        pOutput.accept(ModItems.WASH_BUCKET.get());
                        pOutput.accept(ModItems.DISTILLED_WASH_BUCKET.get());
                        pOutput.accept(ModItems.NEW_MAKE_BUCKET.get());
                        pOutput.accept(ModItems.GLENDARTHUN_BUCKET.get());
                        pOutput.accept(ModBlocks.CHERRYOAK_BARREL_BLOCK.get());
                        pOutput.accept(ModBlocks.BARLEY_STEEP_BLOCK.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
