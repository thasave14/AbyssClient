package dev.abyss.client.api.screen.modmenu.comp.settings.impl

import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.api.screen.modmenu.comp.settings.SettingComponent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.ColorAnimation
import org.lwjgl.input.Keyboard
import java.awt.Color

class KeyComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    val outline = ColorAnimation()

    var listening: Boolean = false

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getWidth(), getHeight(), 60f, skia.paint(Color(5, 5, 5)))

        var text = if(listening) "Listening.." else Keyboard.getKeyName(setting.key)

        skia.drawText(text, Fonts.interRegular(25f), x + getWidth() / 2f - skia.getTextWidth(text, Fonts.interRegular(25f)) / 2f, y + getHeight() / 2f - skia.getTextHeight(text, Fonts.interRegular(25f)) / 2f, skia.paint(Color.WHITE))

        skia.drawRoundedOutline(x, y, getWidth(), getHeight(), 60f, 1.4f, skia.paint( outline.animateAsState(isHovered(), Color.WHITE, Color(0, 0, 0, 0)) ))

        skia.drawText(setting.name, Fonts.interRegular(30f), x + getWidth() + 15f, y + getHeight() / 2f - skia.getTextHeight(setting.name, Fonts.interRegular(30f)) / 2f, skia.paint(
            Color.WHITE))
    }

    override fun onClick(button: Int) {

        if(isHovered()) {
            if(button == 0) {
                listening = true
            }
        } else {
            if(button == 0) {
                listening = false
            }
        }
    }

    override fun onKey(char: Char, key: Int) {

        if(listening) {
            setting.key = key
            listening = false
        }
    }

    override fun getWidth(): Float {
        return 60f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 25f * resolution.scaleFactor
    }
}