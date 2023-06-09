package me.juancarloscp52.panorama_screen.forge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class PanoramaErrorScreen extends ErrorScreen {
    Component arg3;
    public PanoramaErrorScreen(Component arg, Component arg2, Component arg3) {
        super(arg, arg2);
        this.arg3 = arg3;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, this.arg3, this.width / 2, 120, 16777215);
    }
}
