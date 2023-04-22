package me.juancarloscp52.panorama_screen.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.network.chat.Component;

public class PanoramaErrorScreen extends ErrorScreen {
    Component arg3;
    public PanoramaErrorScreen(Component arg, Component arg2, Component arg3) {
        super(arg, arg2);
        this.arg3 = arg3;
    }

    @Override
    public void render(PoseStack arg, int i, int j, float f) {
        super.render(arg, i, j, f);
        drawCenteredString(arg, this.font, this.arg3, this.width / 2, 120, 16777215);
    }
}
