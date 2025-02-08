package dev.abyss.client

import dev.abyss.client.api.config.Config
import dev.abyss.client.api.cosmetic.CosmeticManager
import dev.abyss.client.api.module.ModuleRegistry
import dev.abyss.client.api.module.settings.SettingsManager
import dev.abyss.client.api.screen.HudEditorScreen
import dev.abyss.client.api.screen.modmenu.ModMenuScreen
import dev.abyss.client.event.EventManager
import dev.abyss.client.event.Subscribe
import dev.abyss.client.event.impl.KeyEvent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skija.SkiaRenderer
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import org.lwjgl.opengl.Display

/**
 * @author iLofiz & thasave14
 */
class Abyss : ModInitializer {

    companion object {

        @JvmStatic
        private val instance = Abyss()

        @JvmStatic
        fun getInstance(): Abyss {
            return instance
        }
    }

    @get:JvmName("getSkia")
    lateinit var skia: Skia

    @get:JvmName("getModuleRegistry")
    lateinit var moduleRegistry: ModuleRegistry

    @get:JvmName("getSettingsManager")
    lateinit var settingsManager: SettingsManager

    @get:JvmName("getModMenuScreen")
    lateinit var modMenuScreen: ModMenuScreen

    var mc: MinecraftClient = MinecraftClient.getInstance()

    override fun onInitialize() {
        EventManager.register(this)
    }

    fun onPostInit() {

        SkiaRenderer.create(Display.getWidth(), Display.getHeight())
        this.skia = Skia()

        this.moduleRegistry = ModuleRegistry()
        this.settingsManager = SettingsManager()

        Config.load()
        CosmeticManager.init()

        this.modMenuScreen = ModMenuScreen()
    }

    fun onShutdown() {
        Config.save()
    }

    fun getClientName(): String {
        return "Abyss Client"
    }

    fun getVersion(): Double {
        return 1.0
    }

    fun getTitle(): String {
        return "${getClientName()} v${getVersion()} | 1.8.9"
    }

    @Subscribe
    fun onKey(event: KeyEvent) {

        if(event.key == Abyss.getInstance().settingsManager.getSetting("Hud Editor Keybind").key) {
            if(mc.currentScreen == null) {
                mc.setScreen(HudEditorScreen())
            }
        }
    }
}