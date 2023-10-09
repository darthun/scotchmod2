package net.darthun.scotchmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.darthun.scotchmod.block.ModBlocks;
import net.darthun.scotchmod.block.entity.WashbackBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fluids.FluidStack;

public class WashbackBlockEntityRenderer implements BlockEntityRenderer<WashbackBlockEntity> {
    public WashbackBlockEntityRenderer(BlockEntityRendererProvider.Context context){

    }
    @Override
    public void render(WashbackBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
                       int pPackedLight, int pPackedOverlay) {
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        //Render Wash else render Wort get the color
        //0xFF864B09 wort
        //0xFFA09D89 wash
        FluidStack fluidStack = pBlockEntity.getOutputFluid();
        int tintcolor =0;
        if(fluidStack.isEmpty()){
            fluidStack = pBlockEntity.getFluid();
            tintcolor = 0xFF864B09; //wort
        }
        else{
            tintcolor = 0xFFA09D89; //wash
        }
            //RenderType.translucent()
        //Draw Fluid
        if(!fluidStack.isEmpty()){
            pPoseStack.pushPose();
            this.renderSingleBlock(blockRenderer, ModBlocks.CUSTOM_WATER.get().defaultBlockState(),pPoseStack,pBuffer,
                    pPackedLight,pPackedOverlay,ModelData.EMPTY,null,tintcolor);
            pPoseStack.popPose();
        }

    }

    private int getLightLevel(Level level, BlockPos pos){
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight,sLight);
    }

//    public void renderSingleBlock(BlockState pState, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, net.minecraftforge.client.model.data.ModelData modelData, net.minecraft.client.renderer.RenderType renderType) {
//        RenderShape rendershape = pState.getRenderShape();
//        if (rendershape != RenderShape.INVISIBLE) {
//            switch (rendershape) {
//                case MODEL:
//                    BakedModel bakedmodel = this.getBlockModel(pState);
//                    int i = this.blockColors.getColor(pState, (BlockAndTintGetter)null, (BlockPos)null, 0);
//                    float f = (float)(i >> 16 & 255) / 255.0F;
//                    float f1 = (float)(i >> 8 & 255) / 255.0F;
//                    float f2 = (float)(i & 255) / 255.0F;
//                    for (net.minecraft.client.renderer.RenderType rt : bakedmodel.getRenderTypes(pState, RandomSource.create(42), modelData))
//                        this.modelRenderer.renderModel(pPoseStack.last(), pBufferSource.getBuffer(renderType != null ? renderType : net.minecraftforge.client.RenderTypeHelper.getEntityRenderType(rt, false)), pState, bakedmodel, f, f1, f2, pPackedLight, pPackedOverlay, modelData, rt);
//                    break;
//                case ENTITYBLOCK_ANIMATED:
//                    ItemStack stack = new ItemStack(pState.getBlock());
//                    net.minecraftforge.client.extensions.common.IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, ItemDisplayContext.NONE, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
//            }
//
//        }
//    }

    public void renderSingleBlock(BlockRenderDispatcher pDispatcher,BlockState pState, PoseStack pPoseStack,
                                  MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay,
                                  ModelData modelData, RenderType renderType,int tintcolor) {
        RenderShape rendershape = pState.getRenderShape();

        if (rendershape != RenderShape.INVISIBLE) {
            switch (rendershape) {
                case MODEL:
                    BakedModel bakedmodel = pDispatcher.getBlockModel(pState);
                    int i = tintcolor;
                    float f = (float)(i >> 16 & 255) / 255.0F;
                    float f1 = (float)(i >> 8 & 255) / 255.0F;
                    float f2 = (float)(i & 255) / 255.0F;
                    for (RenderType rt : bakedmodel.getRenderTypes(pState, RandomSource.create(42), modelData))
                        pDispatcher.getModelRenderer().renderModel(pPoseStack.last(), pBufferSource.getBuffer(renderType != null ? renderType : net.minecraftforge.client.RenderTypeHelper.getEntityRenderType(rt, false)), pState, bakedmodel, f, f1, f2, pPackedLight, pPackedOverlay, modelData, rt);
                    break;
                case ENTITYBLOCK_ANIMATED:

                    ItemStack stack = new ItemStack(pState.getBlock());
                    net.minecraftforge.client.extensions.common.IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, ItemDisplayContext.NONE, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
            }

        }
    }
}
