package dev.abyss.client.api.screen.modmenu.comp.settings

import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.ColorAnimation
import dev.abyss.client.utils.animate.SimpleAnimation
import java.awt.Color

class SwitchComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    val animation = SimpleAnimation()

    val outline = ColorAnimation()

    init {
        animation.value = x + 10f
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getWidth(), getHeight(), 60f, skia.paint(Color(5, 5, 5)))

        skia.drawRoundedOutline(x, y, getWidth(), getHeight(), 60f, 1.4f, skia.paint( outline.animateAsState(isHovered(), Color.WHITE, Color(0, 0, 0, 0)) ))

        skia.drawText(setting.name, Fonts.interRegular(30f), x + getWidth() + 15f, y + getHeight() / 2f - skia.getTextHeight(setting.name, Fonts.interRegular(30f)) / 2f, skia.paint(Color.WHITE))

        animation.setAnimation( if(setting.toggled) x + getWidth() - 20 else x + 20f, 25.0)

        skia.drawCircle(animation.value, y + 22, getHeight() - 30f, skia.paint(Color(20, 20, 20)))
    }

    override fun onClick(button: Int) {

        if(isHovered()) {
            if(button == 0) {
                setting.toggled = !setting.toggled
            }
        }
    }

    override fun getWidth(): Float {
        return 25f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 15f * resolution.scaleFactor
    }
}