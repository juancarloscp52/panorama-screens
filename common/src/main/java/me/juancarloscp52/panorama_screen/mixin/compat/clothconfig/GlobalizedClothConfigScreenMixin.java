package me.juancarloscp52.panorama_screen.mixin.compat.clothconfig;

import com.mojang.blaze3d.vertex.PoseStack;
import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.shedaniel.clothconfig2.gui.AbstractConfigScreen;
import me.shedaniel.clothconfig2.gui.GlobalizedClothConfigScreen;
import me.shedaniel.math.Rectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(GlobalizedClothConfigScreen.class)
public abstract class GlobalizedClothConfigScreenMixin extends AbstractConfigScreen {


    protected GlobalizedClothConfigScreenMixin(Screen parent, Component title, ResourceLocation backgroundLocation) {
        super(parent, title, backgroundLocation);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE",target = "Lme/shedaniel/clothconfig2/gui/GlobalizedClothConfigScreen;overlayBackground(Lcom/mojang/blaze3d/vertex/PoseStack;Lme/shedaniel/math/Rectangle;IIIII)V"))
    public void test(GlobalizedClothConfigScreen instance, PoseStack matrices, Rectangle rect, int red, int green, int blue, int startAlpha, int endAlpha){
        if (!PanoramaScreens.settings.shouldApplyToObject(this.getClass().getName()))
            this.overlayBackground(matrices,rect,red,green,blue,startAlpha,endAlpha);
    }
}
