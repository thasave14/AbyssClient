package dev.abyss.client.api.module.impl.hud.keystrokes.key

import dev.abyss.client.Abyss
import dev.abyss.client.utils.animate.ColorAnimation
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.Window
import org.lwjgl.input.Keyboard
import java.awt.Color

class Key {

    val fadeAnim = ColorAnimation()

    fun render(x: Float, y: Float, w: Float, h: Float, radius: Float, background: Boolean, textShadow: Boolean, backgroundColor: Color, backgroundColorPressed: Color, textColor: Color, textColorPressed: Color, key: KeyBinding) {

        val skia = Abyss.getInstance().skia

        var pressed = key.isPressed

        val mc = MinecraftClient.getInstance()

        skia.render {

            if(background) {
                skia.drawRoundedRect(x, y, w, h, radius, skia.paint( if(pressed) backgroundColorPressed else backgroundColor ))
            }
        }

        var text = Keyboard.getKeyName(key.code)

        var resolution = Window(mc)

        var descaledX = x / resolution.scaleFactor
        var descaledY = y / resolution.scaleFactor

        var descaledW = w / resolution.scaleFactor
        var descaledH = h / resolution.scaleFactor

        mc.textRenderer.draw(text, descaledX + descaledW / 2f - mc.textRenderer.getStringWidth(text) / 2f, descaledY + descaledH / 2f - mc.textRenderer.fontHeight / 2f, if(pressed) textColorPressed.rgb else textColor.rgb, textShadow)
    }
}