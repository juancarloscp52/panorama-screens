package me.juancarloscp52.panorama_screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;

import java.util.Arrays;
import java.util.List;

public class Settings {

    private final static List<Class<? extends Screen>> MINECRAFT_IGNORED_SCREENS = Arrays.asList(PackSelectionScreen.class, SocialInteractionsScreen.class);
    public final static List<String> PANORAMA_ALLOW_LIST = Arrays.asList(
            "net.minecraft.",
            "net.optifine.gui.", //Optifine
            "quark.base.client",  //Quark
            "shedaniel.clothconfig2.gui.", //Cloth Config
            "bumblesoftware.fastload.client", // FastLoad
            "fuzs.configmenusforge.client", // Config Menus Forge
            "jellysquid.mods.sodium.client.", // Sodium
            "reeses_sodium_options.client", // Reese's sodium options
            "blamejared.controlling", // Controlling
            "mc.ipnext.gui", // Inventory Profiles Next
            "me.juancarloscp52.spyglass_improvements.client", // Spyglass Improvements
            "dev.tr7zw.skinlayers.", // 3d skin layers
            "mcp.mobius.waila.gui.", // mobius jade
            "dqu.additionaladditions.config", // Additional additions
            "bclib.client.gui.", // BCLib
            "cominixo.betterf3.config.gui", // betterf3
            "pepperbell.continuity.client.config", // Continuity
            "redlimerl.detailab.screen", //detail armor bar
            "kyrptonaught.kyrptconfig.config.screen", //kyrpt config
            "megane.runtime.config.screen", // megane
            "terraformersmc.modmenu.gui.ModMenuOptionsScreen", // Mod menu config screen only
            "yalter.mousetweaks.ConfigScreen", // Mouse Tweaks
            "dev.tr7zw.notenoughanimations.", // Not enough animations
            "ha3.presencefootsteps.", // Presence footsteeps
            "shedaniel.rei.impl.client.gui.credits.", // Roughly enough items Credits screen
            "xaero.common.gui.", // Xaero's minimap
            "xaero.map.gui.", // Xaero's world map
            "juuxel.adorn.client.gui.screen.", //Adorn
            "net.puzzlemc.gui.screen.", //Puzzlemc
            "midnightdust.core.screen.", // Midnigh config core
            "midnightdust.lib.config.", //midnight lib config
            "betternether.config.screen", // Better Nether
            "minenash.enhanced_attack_indicator.config.", // Enhanced attack indicator config
            "jamalam360.jamlib.config.", // Right Click Harvest,
            "darkhax.tipsmod.impl.gui.", // Tips
            "natamus.collective_common_forge.config.",
            "natamus.collective_common_fabric.config.",
            "natamus.collective.fabric.config",
            "natamus.collective.forge.config", // Collective
            "izofar.takesapillage.client.gui", // It takes a Pillage
            "snownee.jade.gui.", // Jade
            "chunksfadein.gui.", // Chunks Fade In
            "colormatic.", // Colmatic
            "deepslatecutting.config.", // Deepslate cutting
            "nicerskies.gui.", //Nicer Skies
            "pingwheel.client." // Ping Wheel


    );
    public final static List<String> PANORAMA_BLOCK_LIST = Arrays.asList(
            ".voicechat.gui",
            "net.optifine.shaders.gui." // shaders screen on optifine does not work.
    );

    public boolean printScreenNames = false;
    public List<String> panoramaAllowList = PANORAMA_ALLOW_LIST;
    public List<String> panoramaBlockList = PANORAMA_BLOCK_LIST;

    public boolean shouldApplyToObject(String classname){
        if(null == classname || classname.trim().isEmpty())
            return false;
        // Check If screen is in whitelist list.
        boolean onWhitelist = false;
        for (String allowedClass : panoramaAllowList) {
            if (classname.contains(allowedClass.trim())){
                onWhitelist=true;
                break;
            }
        }

        // don't apply if not in whitelist
        if(!onWhitelist)
            return false;

        // don't apply if it is in blacklist (this is used for allowing mods but not certain screens).
        for (String blockedClass : panoramaBlockList) {
            if (classname.contains(blockedClass.trim())){
                return false;
            }
        }
        return true;
    }

    public boolean shouldApplyToScreen(Screen screen){
        if (screen != null) {
            // Checks if the screen is a Minecraft screen that would break in any case
            if (MINECRAFT_IGNORED_SCREENS.contains(screen.getClass()))
                return false;

            return shouldApplyToObject(screen.getClass().getName());
        }
        return false;
    }

}
