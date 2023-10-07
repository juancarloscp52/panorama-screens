package me.juancarloscp52.panorama_screen.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.ExperimentsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperimentsScreen.class)
public class ExperimentsScreenMixin extends Screen {


    @Shadow @Final private HeaderAndFooterLayout layout;

    protected ExperimentsScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "renderBackground", at = @At("HEAD"),cancellable = true)
    public void renderBackground (GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci){
        // Background
        super.renderBackground(guiGraphics, mouseX,mouseY,delta);
        guiGraphics.fill(0, 0, this.width, this.height, (100 << 24));

        //Header and footer
        RenderSystem.setShaderColor(0.25f, 0.25f, 0.25f, 1.0f);
        guiGraphics.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0f, 0.0f, this.width, this.layout.getHeaderHeight(), 32, 32);
        guiGraphics.blit(BACKGROUND_LOCATION, 0, this.height-this.layout.getFooterHeight(), 0, 0.0f, 0.0f, this.width, this.height, 32, 32);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Header and footer shadows.
        guiGraphics.fillGradient(0, this.layout.getHeaderHeight(), this.width, this.layout.getHeaderHeight() + 4, -16777216, 0);
        guiGraphics.fillGradient(0, this.height-this.layout.getFooterHeight() - 4, this.width, this.height-this.layout.getFooterHeight(), 0, -16777216);

        ci.cancel();
    }

}
