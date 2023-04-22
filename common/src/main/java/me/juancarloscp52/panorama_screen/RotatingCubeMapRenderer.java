package me.juancarloscp52.panorama_screen;


import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.panorama_screen.mixin.PanoramaRendererAccessor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class RotatingCubeMapRenderer {

    private CubeMap cubeMap = TitleScreen.CUBE_MAP;
    private static RotatingCubeMapRenderer INSTANCE;
    private float time= 0;
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");

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

    public void render(){
        render(1, false);
    }

    public void render(float alpha, boolean titleScreen){
        cubeMap = TitleScreen.CUBE_MAP;
        this.cubeMap.render(Minecraft.getInstance(), Mth.sin(time*0.001F)*5.0F + 25.0F,-this.time*0.1F,alpha);
//        if(!titleScreen){
//            PoseStack matrices = new PoseStack();
//            RenderSystem.setShaderTexture(0,overlay);
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.doBackgroundFade ? (float)Mth.ceil(Mth.clamp(alpha, 0.0F, 1.0F)) : 1.0F);
//            Window window = Minecraft.getInstance().getWindow();
//            GuiComponent.blit(matrices, 0, 0, window.getGuiScaledWidth(), window.getHeight(), 0.0F, 0.0F, 16, 128, 16, 128);
//
//            //Render panorama overlay
//            RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
//            RenderSystem.enableBlend();
//            float f = this.doBackgroundFade ? (float)(Util.getMillis() - this.backgroundFadeStart) / 1000.0f : 1.0f;
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.doBackgroundFade ? (float)Mth.ceil(Mth.clamp(f, 0.0f, 1.0f)) : 1.0f);
//            TitleScreen.blit(matrices, 0, 0, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), 0.0f, 0.0f, 16, 128, 16, 128);
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        }
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
