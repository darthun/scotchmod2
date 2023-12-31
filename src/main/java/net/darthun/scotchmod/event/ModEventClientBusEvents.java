package net.darthun.scotchmod.event;

import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.block.entity.MaltKilnBlockEntity;
import net.darthun.scotchmod.block.entity.ModBlockEntities;
import net.darthun.scotchmod.block.entity.renderer.BarleySteepBlockEntityRenderer;
import net.darthun.scotchmod.block.entity.renderer.WashbackBlockEntityRenderer;
import net.darthun.scotchmod.block.entity.renderer.MashTunBlockEntityRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@Mod.EventBusSubscriber(modid = ScotchMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
//    @SubscribeEvent
//    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
//        event.registerSpriteSet(ModParticles.ALEXANDRITE_PARTICLES.get(), AlexandriteParticles.Provider::new);
//    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.BARLEY_STEEP_BE.get(),
                BarleySteepBlockEntityRenderer::new);
        //TODO MALT_KILN MaltKiln Rendering
//        event.registerBlockEntityRenderer(ModBlockEntities.MALT_KILN_BE.get(),
//                MaltKilnBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.WASHBACK_BE.get(),
                WashbackBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.MASH_TUN_BE.get(),
                MashTunBlockEntityRenderer::new);
    }

}