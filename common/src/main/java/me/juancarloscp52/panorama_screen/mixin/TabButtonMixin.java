package me.juancarloscp52.panorama_screen.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.panorama_screen.PanoramaScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.TabButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TabButton.class)
public abstract class TabButtonMixin extends AbstractWidget {
    @Shadow protected abstract int getTextureY();

    @Shadow public abstract boolean isSelected();

    @Shadow public abstract void renderString(PoseStack poseStack, Font font, int i);

    @Shadow protected abstract void renderFocusUnderline(PoseStack poseStack, Font font, int i);

    private static final ResourceLocation TEXTURE = new ResourceLocation("panorama_screens","textures/gui/tab_button.png");

    public TabButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderWidget", at=@At("HEAD"),cancellable = true)
    public void renderButton(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }
        RenderSystem.setShaderTexture(0, TEXTURE);
        TabButton.blitNineSliced(matrices, this.getX(), this.getY(), this.width, this.height, 2, 2, 2, 0, 130, 24, 0, this.getTextureY());
        if(!this.isSelected()){
            TabButton.fill(matrices,this.getX()+2, this.getY()+6, this.getX()+this.width-2, this.getY()+this.height-2,(100 << 24));
        }
        Font textRenderer = minecraft.font;
        int i = this.active ? -1 : -6250336;
        this.renderString(matrices, textRenderer, i);
        if (this.isSelected()) {
            this.renderFocusUnderline(matrices, textRenderer, i);
        }
        ci.cancel();
    }


    }
