package net.darthun.scotchmod.screen;

import net.darthun.scotchmod.ScotchMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModeMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ScotchMod.MOD_ID);

    public static final RegistryObject<MenuType<BarleySteepMenu>> BARLEY_STEEP_MENU =
            registerMenuType(BarleySteepMenu::new,"barley_steep_menu");

    public static final RegistryObject<MenuType<MaltKilnMenu>> MALT_KILN_MENU =
            registerMenuType(MaltKilnMenu::new,"malt_kiln_menu");

    public static final RegistryObject<MenuType<MashTunMenu>> MASH_TUN_MENU =
            registerMenuType(MashTunMenu::new,"mash_tun_menu");

    public static final RegistryObject<MenuType<WashbackMenu>> WASHBACK_MENU =
            registerMenuType(WashbackMenu::new,"washback_menu");
    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,String name){
        return MENUS.register(name,() -> IForgeMenuType.create(factory));
    }
}
