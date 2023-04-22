package me.juancarloscp52.panorama_screen.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.Minecraft;

public class PanoramaScreensModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent)-> PanoramaScreensFabric.settingsGUI.getConfigScreen(parent, Minecraft.getInstance().level != null);
    }
}
