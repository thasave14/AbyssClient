package dev.abyss.client.api.screen.modmenu.comp.settings

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.ColorAnimation
import org.lwjgl.input.Keyboard
import java.awt.Color

class ModeSelectorComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    val outline = ColorAnimation()

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getWidth(), getHeight(), 60f, skia.paint(Color(5, 5, 5)))

        for(mode in setting.modes) {

        }

        skia.drawRoundedOutline(x, y, getWidth(), getHeight(), 60f, 1.4f, skia.paint( outline.animateAsState(isHovered(), Color.WHITE, Color(0, 0, 0, 0)) ))

        skia.drawText(setting.name, Fonts.interRegular(30f), x + getWidth() + 15f, y + getHeight() / 2f - skia.getTextHeight(setting.name, Fonts.interRegular(30f)) / 2f, skia.paint(
            Color.WHITE))
    }

    fun getModeTextWidth(mode: Int): Float {
        return Abyss.getInstance().skia.getTextWidth(setting.modes[mode], Fonts.interRegular(20f))
    }

    override fun getWidth(): Float {
        return 150f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 25f * resolution.scaleFactor
    }
}