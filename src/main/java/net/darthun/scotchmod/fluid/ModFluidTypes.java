package net.darthun.scotchmod.fluid;

import net.darthun.scotchmod.ScotchMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {

    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ScotchMod.MOD_ID);

    //WORT
    public static final RegistryObject<FluidType> WORT_FLUID_TYPE = registerFluidType("wort_fluid",
            new BaseFluidType(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,0xFF864B09,
                    new Vector3f(134f/255f,75f/255f,9f/255f),
                    FluidType.Properties.create().lightLevel(2).viscosity(5).density(15)));
    //WASH
    public static final RegistryObject<FluidType> WASH_FLUID_TYPE = registerFluidType("wash_fluid",
            new BaseFluidType(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,0xFFA09D89,
                    new Vector3f(160f/255f,157f/255f,137f/255f),
                    FluidType.Properties.create().lightLevel(2).viscosity(5).density(15)));
    //DISTILLED WASH
    public static final RegistryObject<FluidType> DISTILLED_WASH_FLUID_TYPE = registerFluidType("distilled_wash_fluid",
            new BaseFluidType(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,0xA1DE810B,
                    new Vector3f(222f/255f,129f/255f,11f/255f),
                    FluidType.Properties.create().lightLevel(2).viscosity(5).density(15)));
            /*
            * Fluids to add:
Wort RGB 134-75-9 #864B09 DONE HERE
Wash RGB 160-157-137 #A09D89
Distilled Wash RGB 222-129-11 #DE810B
New Make RGB 255-146-16 #FF9210
GlenDarthun RGB 238-81-0 #EE5100*/
    private static RegistryObject<FluidType> registerFluidType(String name, FluidType fluidType){
        return FLUID_TYPES.register(name,()-> fluidType);
    }
    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);
    }
}
