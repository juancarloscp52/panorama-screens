package me.juancarloscp52.panorama_screen.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
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
    @Shadow protected abstract void renderBackground(PoseStack p_93442_);
    @Shadow protected int y0;
    @Shadow protected int y1;
    @Shadow public abstract void setRenderBackground(boolean p_93489_);
    @Shadow public abstract void setRenderTopAndBottom(boolean p_93497_);

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private void renderPanorama(AbstractSelectionList entryListWidget, PoseStack matrices) {
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            this.renderBackground(matrices);
            return;
        }

        RotatingCubeMapRenderer.getInstance().render();
        GuiComponent.fill(matrices, 0, this.y0, minecraft.getWindow().getGuiScaledWidth(), this.y1, (100 << 24));
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void bedrockify$ctor(Minecraft client, int width, int height, int top, int bottom, int itemHeight, CallbackInfo ci) {
        // Prevent the screen background from drawing
        this.setRenderBackground(!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen));
        // Prevent top and bottom bars from drawing (Only on pack Screens)
        this.setRenderTopAndBottom(!(this.minecraft.screen instanceof PackSelectionScreen));
    }

}
