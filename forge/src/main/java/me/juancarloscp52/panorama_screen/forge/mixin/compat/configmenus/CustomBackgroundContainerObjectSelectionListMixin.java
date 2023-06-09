package me.juancarloscp52.panorama_screen.forge.mixin.compat.configmenus;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.configmenusforge.client.gui.components.CustomBackgroundContainerObjectSelectionList;
import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.RotatingCubeMapRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(CustomBackgroundContainerObjectSelectionList.class)
public class CustomBackgroundContainerObjectSelectionListMixin {

    @Inject(method = "render",at=@At(value = "INVOKE",target = "Lfuzs/configmenusforge/client/gui/components/CustomBackgroundContainerObjectSelectionList;getRowLeft()I"))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci){
        if (!PanoramaScreens.settings.shouldApplyToObject(this.getClass().getName())){
            return;
        }
        GuiGraphics guiGraphics = new GuiGraphics(Minecraft.getInstance(),Minecraft.getInstance().renderBuffers().bufferSource());
        RotatingCubeMapRenderer.getInstance().render(guiGraphics);
        guiGraphics.fill(0, 0, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), (100 << 24));
    }


}
