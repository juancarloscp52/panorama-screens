package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin {
    @Shadow
    @Final
    protected Minecraft minecraft;
    @Shadow protected int y0;
    @Shadow protected int y1;
    @Shadow public abstract void setRenderBackground(boolean p_93489_);

    @Shadow private boolean renderBackground;

    @Shadow protected int x0;

    @Shadow protected int x1;

    @Shadow protected abstract void enableScissor(GuiGraphics guiGraphics);

    @Inject(method = "render", at = @At("HEAD"))
    private void renderPanorama(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }

        this.enableScissor(guiGraphics);
        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
        guiGraphics.fill(0, this.y0, minecraft.getWindow().getGuiScaledWidth(), this.y1, (100 << 24));
        guiGraphics.disableScissor();
        this.renderBackground = false;
    }

    /**
     * @reason Render panorama on Configured screens.
     */
    @Inject(method = "render", at=@At(value = "INVOKE",target ="Lnet/minecraft/client/gui/components/AbstractSelectionList;getMaxScroll()I",shift = At.Shift.BEFORE))
    public void renderTopAndBotton(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci){
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }

        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        guiGraphics.fillGradient(RenderType.guiOverlay(), this.x0, this.y0, this.x1, this.y0 + 4, -16777216, 0, 0);
        guiGraphics.fillGradient(RenderType.guiOverlay(), this.x0, this.y1 - 4, this.x1, this.y1, 0, -16777216, 0);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void panoramaScreens$ctor(Minecraft client, int width, int height, int top, int bottom, int itemHeight, CallbackInfo ci) {
        // Prevent the screen background from drawing
        this.setRenderBackground(!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen));
    }


}
