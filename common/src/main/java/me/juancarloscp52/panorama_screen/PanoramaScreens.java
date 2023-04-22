package me.juancarloscp52.panorama_screen;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PanoramaScreens {
    public static final String MOD_ID = "panorama_screens";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static Settings settings;
    public static void init() {
        loadSettings();
    }

    public static void loadSettings() {
        File file = new File("./config/panorama_screens/settings.json");
        Gson gson = new Gson();
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                settings = gson.fromJson(fileReader, Settings.class);
                fileReader.close();
            } catch (IOException e) {
                LOGGER.warn("Could not load bedrockIfy settings: " + e.getLocalizedMessage());
            }
        } else {
            settings = new Settings();
            saveSettings();
        }
    }

    public static void saveSettings() {
        Gson gson = new Gson();
        File file = new File("./config/panorama_screens/settings.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(settings));
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.warn("Could not save bedrockIfy settings: " + e.getLocalizedMessage());
        }
    }
}