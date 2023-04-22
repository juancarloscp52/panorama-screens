package me.juancarloscp52.panorama_screen;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;


public class SettingsGUI {

    Settings settings;

    public Screen getConfigScreen(Screen parent, boolean isTransparent){
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableComponent("panoramaScreens.options.settings"));
        builder.setSavingRunnable(PanoramaScreens::saveSettings);

        settings = PanoramaScreens.settings;

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableComponent("panoramaScreens.options.settings.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableComponent("panoramaScreens.options.settings.general.printScreens"),settings.printScreenNames).setDefaultValue(false).setSaveConsumer(value -> settings.printScreenNames=value).build());
        general.addEntry(entryBuilder.startStrList(new TranslatableComponent("panoramaScreens.options.settings.general.allowList"), settings.panoramaAllowList).setDefaultValue(Settings.PANORAMA_ALLOW_LIST).setSaveConsumer(strings -> settings.panoramaAllowList=strings).build());
        general.addEntry(entryBuilder.startStrList(new TranslatableComponent("panoramaScreens.options.settings.general.blockList"), settings.panoramaBlockList).setDefaultValue(Settings.PANORAMA_BLOCK_LIST).setSaveConsumer(strings -> settings.panoramaBlockList=strings).build());

        return builder.setTransparentBackground(isTransparent).build();
    }

}
