package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin{

    @Shadow @Final private List<GuiEventListener> children;
    @Shadow @Nullable protected Minecraft minecraft;
    @Shadow public int height;
    @Shadow @Final public static ResourceLocation BACKGROUND_LOCATION;
    @Shadow public int width;
    @Unique
    boolean hasRenderedName = false;
    @Unique
    boolean hasList = false;

    /**
     * Renders the rotating cube map on screens instead of the dirt texture if enabled.
     */
    @Inject(method = "renderDirtBackground", at = @At("HEAD"), cancellable = true)
    public void renderTexture(GuiGraphics guiGraphics,CallbackInfo info) {
        if(!PanoramaScreens.settings.shouldApplyToScreen((Screen) ((Object)this)))
            return;

        // If the screen contains a list, manually draw top and bottom bars if it is in a world.
        children.forEach(guiEventListener -> {
            if(guiEventListener instanceof AbstractSelectionList){
                hasList = true;
                if(null!= this.minecraft && this.minecraft.level != null){
                    info.cancel();
                    guiGraphics.setColor(0.25f, 0.25f, 0.25f, 1.0f);
                    guiGraphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0f, 0.0f, this.width, ((AbstractSelectionList<?>) guiEventListener).getY(), 32, 32);
                    guiGraphics.blit(BACKGROUND_LOCATION, 0, ((AbstractSelectionList<?>) guiEventListener).getBottom(), 0, 0.0f, 0.0f, this.width, this.height, 32, 32);
                    guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        });

        // do not render cube map if the screen contains a list.
        if(hasList){
            return;
        }

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
