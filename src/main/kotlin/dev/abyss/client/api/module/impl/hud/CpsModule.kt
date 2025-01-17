package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.hud.TextHudModule
import dev.abyss.client.skia.Skia
import dev.abyss.client.utils.CPSHelper

class CpsModule : TextHudModule() {

    override fun render(skia: Skia, x: Float, y: Float) {
        super.render(skia, x, y)
        CPSHelper.update()
    }

    override fun getText(): String {
        return "CPS: ${CPSHelper.getLeftCPS()} | ${CPSHelper.getRightCPS()}"
    }

    override fun getMetaData(): MetaData {
        return MetaData("CPS", "Display your CPS", Type.HUD)
    }
}