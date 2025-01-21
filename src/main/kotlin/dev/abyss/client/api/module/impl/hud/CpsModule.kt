package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.hud.TextHudModule
import dev.abyss.client.api.module.hud.TitleHudModule
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.Switch
import dev.abyss.client.skia.Skia
import dev.abyss.client.utils.CPSHelper

class CpsModule : TitleHudModule() {

    init {

        Switch(
            name = "Right Click CPS",
            module = this,
            toggled = true,
            category = "General"
        )
    }

    override fun render(skia: Skia, x: Float, y: Float) {
        super.render(skia, x, y)
        CPSHelper.update()
    }

    override fun getMainText(): String {
        return "${CPSHelper.getLeftCPS()}${if(getSetting("Right Click CPS").toggled) " | ${CPSHelper.getRightCPS()}" else ""}"
    }

    override fun getTitle(): String {
        return "CPS"
    }

    override fun getMetaData(): MetaData {
        return MetaData("CPS", "Display your CPS", Type.HUD)
    }
}