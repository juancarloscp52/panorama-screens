package me.juancarloscp52.panorama_screen.mixin;

import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PanoramaRenderer.class)
public interface PanoramaRendererAccessor {
    @Accessor
    CubeMap getCubeMap();
}
