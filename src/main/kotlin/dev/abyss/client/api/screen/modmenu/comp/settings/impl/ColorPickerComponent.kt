package dev.abyss.client.api.screen.modmenu.comp.settings.impl

import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.api.screen.modmenu.comp.settings.SettingComponent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.SimpleAnimation
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window
import java.awt.Color

class ColorPickerComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    var open: Boolean = false

    val popupAnimWidth = SimpleAnimation()
    val popupAnimHeight = SimpleAnimation()

    override fun render(skia: Skia, x: Float, y: Float) {

        var window = Window(MinecraftClient.getInstance())

        var descaledX = x / window.scaleFactor
        var descaledY = y / window.scaleFactor

        var descaledW = getWidth() / window.scaleFactor
        var descaledH = getHeight() / window.scaleFactor

        skia.render {

            skia.drawText(setting.name, Fonts.interRegular(30f), x + getWidth() + 15f, y + getHeight() / 2f - skia.getTextHeight(setting.name, Fonts.interRegular(30f)) / 2f, skia.paint(Color.WHITE))

            skia.drawRoundedRect(x, y, getWidth(), getHeight(), 5f, skia.paint(setting.color))

            if(open) {
                popupAnimWidth.setAnimation(90f, 13.0)
                popupAnimHeight.setAnimation(100f, 13.0)
            }

            //skia.drawRoundedRect(x - 35f, y - 35f, popupAnimWidth.value, popupAnimHeight.value, 12f, skia.paint(Color()))
        }
    }

    override fun onClick(button: Int) {

        if(isHovered()) {
            if(button == 0) {
                open = !open
            }
        }
    }

    override fun getWidth(): Float {
        return 20f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    override fun getHeight(): Float {
        return 20f * Window(MinecraftClient.getInstance()).scaleFactor
    }
}