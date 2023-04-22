package me.juancarloscp52.panorama_screen;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class SettingsGUI {

    Settings settings;

    public Screen getConfigScreen(Screen parent, boolean isTransparent){
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("panoramaScreens.options.settings"));
        builder.setSavingRunnable(PanoramaScreens::saveSettings);

        settings = PanoramaScreens.settings;

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("panoramaScreens.options.settings.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("panoramaScreens.options.settings.general.printScreens"),settings.printScreenNames).setDefaultValue(false).setSaveConsumer(value -> settings.printScreenNames=value).build());
        general.addEntry(entryBuilder.startStrList(Component.translatable("panoramaScreens.options.settings.general.allowList"), settings.panoramaAllowList).setDefaultValue(Settings.PANORAMA_ALLOW_LIST).setSaveConsumer(strings -> settings.panoramaAllowList=strings).build());
        general.addEntry(entryBuilder.startStrList(Component.translatable("panoramaScreens.options.settings.general.blockList"), settings.panoramaBlockList).setDefaultValue(Settings.PANORAMA_BLOCK_LIST).setSaveConsumer(strings -> settings.panoramaBlockList=strings).build());

        return builder.setTransparentBackground(isTransparent).build();
    }

}
