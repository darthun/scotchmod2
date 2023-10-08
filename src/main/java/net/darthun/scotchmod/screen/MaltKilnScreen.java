package net.darthun.scotchmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.darthun.scotchmod.ScotchMod;
import net.darthun.scotchmod.screen.renderer.FluidTankRenderer;
import net.darthun.scotchmod.utils.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class MaltKilnScreen extends AbstractContainerScreen<MaltKilnMenu> {
    //start
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ScotchMod.MOD_ID,"textures/gui/malt_kiln_gui.png");


    //end
    public MaltKilnScreen(MaltKilnMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 70;
        this.titleLabelY = 5;
        this.inventoryLabelX = 64;
        this.titleLabelX = 64;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 75, y + 26, 176, 0, menu.getScaledProgress(), 21);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) /2;
        int y = (height - imageHeight)/2;

        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);

    }

}
