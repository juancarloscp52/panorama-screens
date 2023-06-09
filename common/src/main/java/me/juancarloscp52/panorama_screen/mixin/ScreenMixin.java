package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Screen.class)
public abstract class ScreenMixin{


    boolean hasRenderedName = false;

    /**
     * Renders the rotating cube map on screens instead of the dirt texture if enabled.
     */
    @Inject(method = "renderDirtBackground", at = @At("HEAD"), cancellable = true)
    public void renderTexture(GuiGraphics guiGraphics,CallbackInfo info) {
        if(!PanoramaScreens.settings.shouldApplyToScreen((Screen) ((Object)this)))
            return;
        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
        info.cancel();
    }

    @Inject(method = "render", at=@At("HEAD"))
    public void render (GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci){
        if(PanoramaScreens.settings.printScreenNames && !hasRenderedName){
            PanoramaScreens.LOGGER.info("Current screen: "+this.getClass().getName());
            hasRenderedName=true;
        }
    }
}
