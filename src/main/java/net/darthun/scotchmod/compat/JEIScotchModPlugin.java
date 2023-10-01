package net.darthun.scotchmod.compat;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.darthun.scotchmod.ScotchMod;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIScotchModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ScotchMod.MOD_ID,"jei_plugin");
    }
}
