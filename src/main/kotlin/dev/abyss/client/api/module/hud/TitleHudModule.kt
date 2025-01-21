package dev.abyss.client.api.module.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.Switch
import net.minecraft.client.MinecraftClient

abstract class TitleHudModule : TextHudModule() {

    init {

        CategoryDivider(
            name = "General",
            module = this
        )

        Switch(
            name = "Reversed Title",
            module = this,
            toggled = true,
            category = "General"
        )
    }

    override fun getText(): String {
        return "${(if(getSetting("Reversed Title").toggled) "" else "${getTitle()}: ")}${getMainText()}${(if(getSetting("Reversed Title").toggled)" ${getTitle()}" else "")}"
    }

    abstract fun getMainText(): String

    abstract fun getTitle(): String
}