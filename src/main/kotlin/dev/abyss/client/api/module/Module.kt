package dev.abyss.client.api.module

import com.google.gson.annotations.Expose
import dev.abyss.client.Abyss
import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.event.EventManager
import net.minecraft.client.MinecraftClient

abstract class Module : IMetaDataProvider {

    @get:JvmName("isToggled")
    var toggled: Boolean = true

    @Expose(serialize = false)
    val mc = MinecraftClient.getInstance()

    val settings = mutableListOf<Setting>()

    init {
        EventManager.register(this)
    }

    fun onEnable() {
        EventManager.register(this)
    }

    fun onDisable() {
        EventManager.unregister(this)
    }

    fun onToggle() {}

    fun setToggled() {
        this.toggled = !this.toggled

        onToggle()

        if(this.toggled) {
            onEnable()
        } else {
            onDisable()
        }
    }

    fun getSetting(name: String): Setting {
        return Abyss.getInstance().settingsManager.getSetting(name, this)
    }
}