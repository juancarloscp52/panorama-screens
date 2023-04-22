package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {

    @Inject(method = "render", at=@At("HEAD"),cancellable = true)
    public void renderBedrockIfyCubeMap(float delta, float alpha, CallbackInfo ci){
        RotatingCubeMapRenderer.getInstance().render(alpha, true);
        ci.cancel();
    }

}
