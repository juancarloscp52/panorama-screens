package me.juancarloscp52.panorama_screen.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.TabButton;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screens.Screen.BACKGROUND_LOCATION;

@Mixin(TabNavigationBar.class)
public abstract class TabNavigationBarMixin {

    @Shadow @Final private ImmutableList<TabButton> tabButtons;
    @Shadow @Final private GridLayout layout;
    @Shadow @Nullable protected abstract TabButton currentTabButton();
    @Shadow private int width;
    @Shadow protected abstract int currentTabIndex();

    private static final ResourceLocation HEADER_SEPARATOR_TEXTURE = new ResourceLocation("panorama_screens","textures/gui/header_separator.png");
    private static final ResourceLocation FOOTER_SEPARATOR_TEXTURE = new ResourceLocation("panorama_screens","textures/gui/footer_separator.png");

    @Inject(method = "render",at=@At("HEAD"),cancellable = true)
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }

        //Panorama background
        RotatingCubeMapRenderer.getInstance().render(guiGraphics,1,false);

        //Footer background
        RenderSystem.setShaderColor(0.25f, 0.25f, 0.25f, 1.0f);
        guiGraphics.blit(BACKGROUND_LOCATION, 0, minecraft.getWindow().getGuiScaledHeight()-36, 0, 0.0f, 0.0f, minecraft.getWindow().getGuiScaledWidth(), 36, 32, 32);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Footer separator
        guiGraphics.blit(FOOTER_SEPARATOR_TEXTURE, 0, Mth.roundToward(minecraft.getWindow().getGuiScaledHeight() - 36 - 2, 2), 0.0f, 0.0f, minecraft.getWindow().getGuiScaledWidth(), 2, 32, 2);

        // Darken panorama background
        guiGraphics.fill(0, 0, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getScreenHeight()-36, (100 << 24));

        //Header backgrounds
        RenderSystem.setShaderColor(0.25f, 0.25f, 0.25f, 1.0f);

        //Left background
        guiGraphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0f, 0.0f, this.currentTabButton().getX(), 22, 32, 32);
        //Right background
        //Compute dirt texture horizontal offset, so that the texture stays in the same position regardless selected button.
        float textureHOffset = ((this.currentTabIndex()+1)*this.currentTabButton().getWidth()) % 16;
        guiGraphics.blit(BACKGROUND_LOCATION, this.currentTabButton().getX()+this.currentTabButton().getWidth(), 0, 0, textureHOffset, 0.0f, minecraft.getWindow().getGuiScaledWidth(), 22, 32, 32);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        guiGraphics.blit(HEADER_SEPARATOR_TEXTURE, 0, this.layout.getY() + this.layout.getHeight() - 2, 0.0f, 0.0f, this.currentTabButton().getX()+2, 2, 32, 2);
        guiGraphics.blit(HEADER_SEPARATOR_TEXTURE, this.currentTabButton().getX()+this.currentTabButton().getWidth(), this.layout.getY() + this.layout.getHeight() - 2, textureHOffset, 0.0f, this.width, 2, 32, 2);

        //Render buttons
        for (TabButton tabButtonWidget : this.tabButtons) {
            tabButtonWidget.render(guiGraphics, mouseX, mouseY, delta);
        }
        ci.cancel();
    }

}
