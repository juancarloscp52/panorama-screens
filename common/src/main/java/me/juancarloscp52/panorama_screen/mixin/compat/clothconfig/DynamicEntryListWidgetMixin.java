package me.juancarloscp52.panorama_screen.mixin.compat.clothconfig;


import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(DynamicEntryListWidget.class)
public class DynamicEntryListWidgetMixin {

    @Shadow public int top;

    @Shadow public int bottom;

    @Inject(method = "renderBackBackground", at=@At("HEAD"),cancellable = true)
    public void renderBackBackground(GuiGraphics guiGraphics, BufferBuilder buffer, Tesselator tessellator, CallbackInfo ci){
        if (!PanoramaScreens.settings.shouldApplyToObject(this.getClass().getName())){
            return;
        }
        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
        guiGraphics.fill(0, this.top, Minecraft.getInstance().getWindow().getGuiScaledWidth(), this.bottom, (100 << 24));
        ci.cancel();

    }

}
