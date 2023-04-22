package me.juancarloscp52.panorama_screen.forge;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.SettingsGUI;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(PanoramaScreens.MOD_ID)
public class PanoramaScreensForge {
    public PanoramaScreensForge() {
        PanoramaScreens.init();

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (minecraft, screen) -> {
                            if(!ModList.get().isLoaded("cloth_config"))
                                return new PanoramaErrorScreen(new TranslatableComponent("panoramaScreens.clothConfigError"),new TranslatableComponent("panoramaScreens.clothConfigError.description1"),new TranslatableComponent("panoramaScreens.clothConfigError.description2"));

                            return new SettingsGUI().getConfigScreen(screen,false);
                        })
        );

    }

}