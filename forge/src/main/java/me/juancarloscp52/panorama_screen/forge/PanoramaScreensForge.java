package me.juancarloscp52.panorama_screen.forge;

import me.juancarloscp52.panorama_screen.PanoramaScreens;
import me.juancarloscp52.panorama_screen.SettingsGUI;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(PanoramaScreens.MOD_ID)
@Mod.EventBusSubscriber(modid = PanoramaScreens.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PanoramaScreensForge {
    public PanoramaScreensForge() {
        PanoramaScreens.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, screen) -> {
                            if(!ModList.get().isLoaded("cloth_config"))
                                return new PanoramaErrorScreen(Component.translatable("panoramaScreens.clothConfigError"),Component.translatable("panoramaScreens.clothConfigError.description1"),Component.translatable("panoramaScreens.clothConfigError.description2"));

                            return new SettingsGUI().getConfigScreen(screen,false);
                        })
        );
    }

}