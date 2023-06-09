package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin {
    @Shadow
    @Final
    protected Minecraft minecraft;
    @Shadow protected abstract void renderBackground(GuiGraphics guiGraphics);
    @Shadow protected int y0;
    @Shadow protected int y1;
    @Shadow public abstract void setRenderBackground(boolean p_93489_);
    @Shadow public abstract void setRenderTopAndBottom(boolean p_93497_);

    @Shadow private boolean renderBackground;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void renderPanorama(AbstractSelectionList instance, GuiGraphics guiGraphics) {
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            this.renderBackground(guiGraphics);
            return;
        }

        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
        guiGraphics.fill(0, this.y0, minecraft.getWindow().getGuiScaledWidth(), this.y1, (100 << 24));
    }

    /**
     * @reason Render panorama on Configured screens.
     */
    @Inject(method = "render", at=@At(value = "INVOKE",target ="Lnet/minecraft/client/gui/components/AbstractSelectionList;getRowLeft()I"))
    public void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci){
        // Only apply if it is a Configured screen and is on the allow list
        if (!(PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen) && minecraft.screen.getClass().getName().contains("configured.client."))) {
            return;
        }

        //Disable render background.
        this.renderBackground = false;
        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void bedrockify$ctor(Minecraft client, int width, int height, int top, int bottom, int itemHeight, CallbackInfo ci) {
        // Prevent the screen background from drawing
        this.setRenderBackground(!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen));
        // Prevent top and bottom bars from drawing (Only on pack Screens)
        this.setRenderTopAndBottom(!(this.minecraft.screen instanceof PackSelectionScreen));
    }

}
