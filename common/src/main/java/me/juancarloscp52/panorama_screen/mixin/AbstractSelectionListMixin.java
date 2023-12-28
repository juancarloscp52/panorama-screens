package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin extends AbstractContainerWidget {
    @Shadow
    @Final
    protected Minecraft minecraft;

    public AbstractSelectionListMixin(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Shadow public abstract void setRenderBackground(boolean p_93489_);

    @Shadow private boolean renderBackground;

    @Shadow protected abstract void enableScissor(GuiGraphics guiGraphics);

    @Inject(method = "renderWidget", at = @At("HEAD"))
    private void renderPanorama(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }

        this.enableScissor(guiGraphics);
        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
        guiGraphics.fill(0, this.getY(), minecraft.getWindow().getGuiScaledWidth(), this.getBottom(), (100 << 24));
        guiGraphics.disableScissor();
        this.renderBackground = false;
    }

    /**
     * @reason Render panorama on Configured screens.
     */
    @Inject(method = "renderWidget", at=@At(value = "INVOKE",target ="Lnet/minecraft/client/gui/components/AbstractSelectionList;getMaxScroll()I",shift = At.Shift.BEFORE))
    public void renderTopAndBotton(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci){
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }

        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        guiGraphics.fillGradient(RenderType.guiOverlay(), this.getX(), this.getY(), this.getRight(), this.getY() + 4, -16777216, 0, 0);
        guiGraphics.fillGradient(RenderType.guiOverlay(), this.getX(), this.getBottom() - 4, this.getRight(), this.getBottom(), 0, -16777216, 0);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void panoramaScreens$ctor(Minecraft minecraft, int i, int j, int k, int l, CallbackInfo ci) {
        // Prevent the screen background from drawing
        this.setRenderBackground(!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen));
    }


}
