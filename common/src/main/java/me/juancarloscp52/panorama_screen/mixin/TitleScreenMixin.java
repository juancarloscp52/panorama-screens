package me.juancarloscp52.panorama_screen.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow @Final private PanoramaRenderer panorama;

    @Shadow @Final private static ResourceLocation PANORAMA_OVERLAY;

    @Shadow @Final private boolean fading;

    @Shadow private long fadeInStart;

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "render", at=@At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V"))
    public void updatePanorama(PoseStack matrix, int mouseX, int mouseY, float delta, CallbackInfo ci){
        RotatingCubeMapRenderer.getInstance().update(panorama, PANORAMA_OVERLAY, fading, fadeInStart);
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 0),index = 1)
    private ResourceLocation getOverlayProd(ResourceLocation location) {
        RotatingCubeMapRenderer.getInstance().updateOverlayId(location);
        return location;
    }

}
