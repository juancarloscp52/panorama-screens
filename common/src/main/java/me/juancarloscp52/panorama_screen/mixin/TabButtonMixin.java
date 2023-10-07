package me.juancarloscp52.panorama_screen.mixin;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.TabButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TabButton.class)
public abstract class TabButtonMixin extends AbstractWidget {
    @Shadow public abstract boolean isSelected();

    @Shadow public abstract void renderString(GuiGraphics guiGraphics, Font font, int i);

    @Shadow protected abstract void renderFocusUnderline(GuiGraphics guiGraphics, Font font, int i);

    @Unique
    private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("panorama_screens", "widget/tab_selected"), new ResourceLocation("panorama_screens", "widget/tab"), new ResourceLocation("panorama_screens", "widget/tab_selected_highlighted"), new ResourceLocation("panorama_screens", "widget/tab_highlighted"));

    public TabButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderWidget", at=@At("HEAD"),cancellable = true)
    public void renderButton(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!PanoramaScreens.settings.shouldApplyToScreen(minecraft.screen)) {
            return;
        }
        guiGraphics.blitSprite(SPRITES.get(this.isSelected(), this.isHovered()), this.getX(), this.getY(), this.width, this.height);
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
