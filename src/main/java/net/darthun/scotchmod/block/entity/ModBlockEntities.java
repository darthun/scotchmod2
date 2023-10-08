package net.darthun.scotchmod.block.entity;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ScotchMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<BarleySteepBlockEntity>> BARLEY_STEEP_BE =
            BLOCK_ENTITIES.register("barley_steep_block_entity",()->
                    BlockEntityType.Builder.of(BarleySteepBlockEntity::new,
                            ModBlocks.BARLEY_STEEP_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MaltKilnBlockEntity>> MALT_KILN_BE =
            BLOCK_ENTITIES.register("malt_kiln_block_entity",()->
                    BlockEntityType.Builder.of(MaltKilnBlockEntity::new,
                            ModBlocks.MALT_KILN_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
