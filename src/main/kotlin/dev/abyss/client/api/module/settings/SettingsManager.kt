package dev.abyss.client.api.module.settings

import dev.abyss.client.api.module.Module
import org.lwjgl.input.Keyboard

class SettingsManager {

    val settings = mutableListOf<Setting>()

    init {

        settings.add(Setting("Visual", null))
        settings.add(Setting("No Hurt Cam", null, true, "Visual"))
        settings.add(Setting("Minimal View Bobbing", null, true, "Visual"))
        settings.add(Setting("Client", null))
        settings.add(Setting("Hud Editor Keybind", null, Keyboard.KEY_RSHIFT, "Client"))
    }

    fun getSetting(name: String, module: Module): Setting {
        return module.settings.stream().filter { it.name == name && it.module == module }.findFirst().orElse(null)
    }

    fun getSetting(name: String): Setting {
        return settings.stream().filter { it.name == name }.findFirst().orElse(null)
    }
}