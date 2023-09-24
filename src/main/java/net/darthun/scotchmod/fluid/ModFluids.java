package net.darthun.scotchmod.fluid;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, ScotchMod.MOD_ID);

    // WORT
    public static final RegistryObject<FlowingFluid> SOURCE_WORT = FLUIDS.register("wort_fluid",
            ()-> new ForgeFlowingFluid.Source(ModFluids.WORT_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_WORT = FLUIDS.register("flowing_wort_fluid",
            ()-> new ForgeFlowingFluid.Flowing(ModFluids.WORT_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties WORT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.WORT_FLUID_TYPE,SOURCE_WORT,FLOWING_WORT)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.WORT_BLOCK).bucket(ModItems.WORT_BUCKET);

    // WASH
    public static final RegistryObject<FlowingFluid> SOURCE_WASH = FLUIDS.register("wash_fluid",
            ()-> new ForgeFlowingFluid.Source(ModFluids.WASH_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_WASH = FLUIDS.register("flowing_wash_fluid",
            ()-> new ForgeFlowingFluid.Flowing(ModFluids.WASH_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties WASH_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.WASH_FLUID_TYPE,SOURCE_WASH,FLOWING_WASH)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.WASH_BLOCK).bucket(ModItems.WASH_BUCKET);
    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }
}
