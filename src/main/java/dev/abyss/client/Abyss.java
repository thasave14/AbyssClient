package dev.abyss.client;

import dev.abyss.client.api.config.Config;
import dev.abyss.client.api.cosmetic.CosmeticManager;
import dev.abyss.client.api.module.ModuleRegistry;
import dev.abyss.client.api.module.settings.SettingsManager;
import dev.abyss.client.api.screen.HudEditorScreen;
import dev.abyss.client.api.screen.modmenu.ModMenuScreen;
import dev.abyss.client.event.EventManager;
import dev.abyss.client.event.Subscribe;
import dev.abyss.client.event.impl.KeyEvent;
import dev.abyss.client.skia.Skia;
import dev.abyss.client.skija.SkiaRenderer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.Display;

/**
 * @author iLofiz
 * (i didn't bother to use lombok cuz this client is mainly built with kotlin)
 */
public class Abyss implements ModInitializer {

    private static Abyss instance;

    public static Abyss getInstance() {
        if(instance == null) instance = new Abyss();
        return instance;
    }

    private Skia skia;
    private ModuleRegistry moduleRegistry;
    private SettingsManager settingsManager;

    private ModMenuScreen modMenuScreen;

    @Override
    public void onInitialize() {
        EventManager.register(this);
    }

    public void onPostInit() {

        SkiaRenderer.create(Display.getWidth(), Display.getHeight());
        this.skia = new Skia();

        this.moduleRegistry = new ModuleRegistry();
        this.settingsManager = new SettingsManager();

        Config.load();
        CosmeticManager.init();

        this.modMenuScreen = new ModMenuScreen();
    }

    public void onShutdown() {
        Config.save();
    }

    public Skia getSkia() {
        return skia;
    }

    public ModuleRegistry getModuleRegistry() {
        return moduleRegistry;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public ModMenuScreen getModMenuScreen() {
        return modMenuScreen;
    }

    public String getTitle() {
        return "Abyss Client v1.0.0 | 1.8.9";
    }

    @Subscribe
    public void onKey(KeyEvent event) {

        if(event.getKey() == Abyss.getInstance().getSettingsManager().getSetting("Hud Editor Keybind").getKey()) {

            if(MinecraftClient.getInstance().currentScreen == null) {
                MinecraftClient.getInstance().setScreen(new HudEditorScreen());
            }
        }
    }
}
