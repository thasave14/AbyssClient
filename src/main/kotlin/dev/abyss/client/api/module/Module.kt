package dev.abyss.client.api.module

import com.google.gson.annotations.Expose
import dev.abyss.client.Abyss
import dev.abyss.client.api.module.settings.Setting
import net.minecraft.client.MinecraftClient

abstract class Module : IMetaDataProvider {

    @get:JvmName("isToggled")
    var toggled: Boolean = true

    fun onEnable() {}

    fun onDisable() {}

    fun onToggle() {}

    @Expose(serialize = false)
    val mc = MinecraftClient.getInstance()

    val settings = mutableListOf<Setting>()

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