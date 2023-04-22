package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {


    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at=@At(value = "INVOKE",target = "Lnet/minecraft/client/Minecraft;getDeltaFrameTime()F",ordinal = 1))
    public void updatePanorama(float tickDelta, long startTime, boolean tick, CallbackInfo ci){
        RotatingCubeMapRenderer.getInstance().addPanoramaTime(minecraft.getDeltaFrameTime());
    }

}
