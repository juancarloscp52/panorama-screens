package me.juancarloscp52.panorama_screen;


import com.mojang.blaze3d.systems.RenderSystem;
import me.juancarloscp52.panorama_screen.mixin.PanoramaRendererAccessor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class RotatingCubeMapRenderer {

    private CubeMap cubeMap = TitleScreen.CUBE_MAP;
    private static RotatingCubeMapRenderer INSTANCE;
    private float time= 0;
    private boolean doBackgroundFade;
    private long backgroundFadeStart;
    private ResourceLocation overlay = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");

    private RotatingCubeMapRenderer(){}

    public static RotatingCubeMapRenderer getInstance(){
        if(INSTANCE==null)
            INSTANCE=new RotatingCubeMapRenderer();
        return INSTANCE;
    }

    public void addPanoramaTime(float delta){
        this.time += delta;
    }

    public void render(GuiGraphics guiGraphics){
        render(guiGraphics, 1, false);
    }

    public void render(GuiGraphics guiGraphics, float alpha, boolean titleScreen){
        this.cubeMap.render(Minecraft.getInstance(), Mth.sin(time*0.001F)*5.0F + 25.0F,-this.time*0.1F,alpha);
        if(!titleScreen){
            //Render panorama overlay
            RenderSystem.enableBlend();
            float f = this.doBackgroundFade ? (float)(Util.getMillis() - this.backgroundFadeStart) / 1000.0f : 1.0f;
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.doBackgroundFade ? (float)Mth.ceil(Mth.clamp(f, 0.0f, 1.0f)) : 1.0f);
            guiGraphics.blit(overlay, 0, 0, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), 0.0f, 0.0f, 16, 128, 16, 128);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public void update(PanoramaRenderer renderer, ResourceLocation panoramaOverlay, boolean doBackgroundFade, long backgroundFadeStart) {
        this.cubeMap = ((PanoramaRendererAccessor) renderer).getCubeMap();
        this.overlay = panoramaOverlay;
        this.doBackgroundFade = doBackgroundFade;
        this.backgroundFadeStart = backgroundFadeStart;
    }
    public void updateOverlayId(ResourceLocation panoramaOverlay){
        this.overlay = panoramaOverlay;
    }

}
