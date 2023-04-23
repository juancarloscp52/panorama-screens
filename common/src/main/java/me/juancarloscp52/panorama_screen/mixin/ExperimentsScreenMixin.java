package me.juancarloscp52.panorama_screen.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
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

    @Inject(method = "render", at = @At("HEAD"),cancellable = true)
    public void render (PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci){
        // Background
        this.renderBackground(matrices);
        GuiComponent.fill(matrices, 0, 0, this.width, this.height, (100 << 24));

        //Header and footer
        RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(0.25f, 0.25f, 0.25f, 1.0f);
        Screen.blit(matrices, 0, 0, 0, 0.0f, 0.0f, this.width, this.layout.getHeaderHeight(), 32, 32);
        Screen.blit(matrices, 0, this.height-this.layout.getFooterHeight(), 0, 0.0f, 0.0f, this.width, this.height, 32, 32);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Header and footer shadows.
        AbstractSelectionList.fillGradient(matrices, 0, this.layout.getHeaderHeight(), this.width, this.layout.getHeaderHeight() + 4, -16777216, 0);
        AbstractSelectionList.fillGradient(matrices, 0, this.height-this.layout.getFooterHeight() - 4, this.width, this.height-this.layout.getFooterHeight(), 0, -16777216);

        super.render(matrices, mouseX, mouseY, delta);

        ci.cancel();
    }

}
