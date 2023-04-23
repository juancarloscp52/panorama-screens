package me.juancarloscp52.panorama_screen.fabric;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.SettingsGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class PanoramaScreensFabric implements ClientModInitializer {

    public static SettingsGUI settingsGUI= new SettingsGUI();

    @Override
    public void onInitializeClient() {
        PanoramaScreens.init();
        if(FabricLoader.getInstance().isModLoaded("languagereload"))
            PanoramaScreens.isLanguageReloadLoaded = true;
    }
}