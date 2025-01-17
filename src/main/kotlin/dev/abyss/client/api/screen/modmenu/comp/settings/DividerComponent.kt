package dev.abyss.client.api.screen.modmenu.comp.settings

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import java.awt.Color

class DividerComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    override fun render(skia: Skia, x: Float, y: Float) {
        skia.drawText(setting.name, Fonts.interMedium(25f), x, y, skia.paint(Color.WHITE))
    }

    override fun getWidth(): Float {
        return Abyss.getInstance().skia.getTextWidth(setting.name, Fonts.interMedium(25f))
    }

    override fun getHeight(): Float {
        return Abyss.getInstance().skia.getTextHeight(setting.name, Fonts.interMedium(25f))
    }
}