package dev.abyss.client.api.screen.modmenu.comp.settings.impl

import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.api.screen.modmenu.comp.settings.SettingComponent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.ColorAnimation
import net.minecraft.client.MinecraftClient
import net.minecraft.util.SharedConstants
import org.lwjgl.input.Keyboard
import java.awt.Color

class TextComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    var focused: Boolean = false
    var allSelected: Boolean = false

    var cursorPos: Int = 0

    val outline = ColorAnimation()

    var popupTime: Long = 0L
    var show: Boolean = true

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getWidth(), getHeight(), 60f, skia.paint(Color(5, 5, 5)))

        if(popupTime != 100L) {
            popupTime++
            show = popupTime == 100L
        } else {
            popupTime = 0
            show = false
        }

        if(setting.text.isEmpty()) {

            if(focused) {

                if(show) {
                    skia.drawText("|", Fonts.interRegular(25f), x + 25f, y + getHeight() / 2f - skia.getTextHeight("|", Fonts.interRegular(25f)) / 2f, skia.paint(Color.WHITE))
                }

            } else {
                skia.drawText(setting.placeHolder, Fonts.interRegular(25f), x + 25f, y + getHeight() / 2f - skia.getTextHeight(setting.placeHolder, Fonts.interRegular(25f)) / 2f, skia.paint(Color.WHITE))
            }
        } else {
            skia.drawText(setting.text, Fonts.interRegular(25f), x + 25f, y + getHeight() / 2f - skia.getTextHeight(setting.text, Fonts.interRegular(25f)) / 2f, skia.paint(
                Color.WHITE))
        }

        skia.drawRoundedOutline(x, y, getWidth(), getHeight(), 60f, 1.4f, skia.paint( outline.animateAsState(isHovered(), Color.WHITE, Color(0, 0, 0, 0)) ))

        skia.drawText(setting.name, Fonts.interRegular(30f), x + getWidth() + 15f, y + getHeight() / 2f - skia.getTextHeight(setting.name, Fonts.interRegular(30f)) / 2f, skia.paint(
            Color.WHITE))
    }

    override fun onClick(button: Int) {

        if(isHovered()) {
            if(button == 0) {
                focused = true
            }
        } else {

            if(button == 0) {
                focused = false
            }
        }
    }

    override fun onKey(char: Char, key: Int) {

        if(!focused) {
            return
        }

        when(key) {
            Keyboard.KEY_BACK -> {
                remove()
            }
            Keyboard.KEY_RIGHT -> {
                moveCursor(1)
            }
            Keyboard.KEY_LEFT -> {
                moveCursor(-1)
            }
            Keyboard.KEY_A -> {

                if(isCtrlKeyDown()) {
                    allSelected = true
                    cursorPos = setting.text.length
                }
            }
            else -> {
                if(SharedConstants.isValidChar(char)) {
                    type(char.toString())
                }
            }
        }
    }

    fun isCtrlKeyDown(): Boolean {
        return if (MinecraftClient.IS_MAC) Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) else Keyboard.isKeyDown(
            29
        ) || Keyboard.isKeyDown(157)
    }

    fun type(text: String) {

        if(allSelected) {
            cursorPos = 0
            setting.text = ""
        }

        val builder = StringBuilder(setting.text)
        builder.insert(cursorPos, text)
        setting.text = builder.toString()
        moveCursor(1)
    }

    fun remove() {

        if(setting.text.isNotEmpty()) {

            if(allSelected) {
                allSelected = false
                cursorPos = 0
                setting.text = ""
            } else {
                moveCursor(-1)
                val builder = StringBuilder(setting.text)
                builder.deleteCharAt(cursorPos)
                setting.text = builder.toString()
            }
        }
    }

    fun moveCursor(direction: Int) {

        allSelected = false

        if(direction < 0) {
            if(cursorPos > 0) {
                cursorPos--
            }
        } else if(direction > 0) {

            if(cursorPos < setting.text.length) {
                cursorPos++
            }
        }
    }

    override fun getWidth(): Float {
        return 90f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 25f * resolution.scaleFactor
    }
}