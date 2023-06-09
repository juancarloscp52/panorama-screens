package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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

    @Shadow public abstract void renderString(GuiGraphics guiGraphics, Font font, int i);

    @Shadow protected abstract void renderFocusUnderline(GuiGraphics guiGraphics, Font font, int i);

    private static final ResourceLocation TEXTURE = new ResourceLocation("panorama_screens","textures/gui/tab_button.png");

    public TabButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderWidget", at=@At("HEAD"),cancellable = true)
    public void renderButton(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }
        guiGraphics.blitNineSliced(TEXTURE,this.getX(), this.getY(), this.width, this.height, 2, 2, 2, 0, 130, 24, 0, this.getTextureY());
        if(!this.isSelected()){
            guiGraphics.fill(this.getX()+2, this.getY()+6, this.getX()+this.width-2, this.getY()+this.height-2,(100 << 24));
        }
        Font textRenderer = minecraft.font;
        int i = this.active ? -1 : -6250336;
        this.renderString(guiGraphics, textRenderer, i);
        if (this.isSelected()) {
            this.renderFocusUnderline(guiGraphics, textRenderer, i);
        }
        ci.cancel();
    }


    }
