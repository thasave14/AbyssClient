package dev.abyss.client.api.module.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.ColorPicker
import dev.abyss.client.api.module.settings.Slider
import dev.abyss.client.api.module.settings.Switch
import dev.abyss.client.skia.Skia
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window
import java.awt.Color

abstract class TextHudModule : HudModule() {

    init {

        CategoryDivider("Background ", this)

        Switch(
            name = "Background",
            module = this,
            toggled = true,
            category = "Background"
        )

        ColorPicker(
            name = "Background Color",
            module = this,
            color = Color(0, 0, 0, 100),
            category = "Background"
        )

        Slider(
            name = "Corner Radius",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 20f,
            category = "Background"
        )

        CategoryDivider("Text ", this)

        Switch(
            name = "Text Shadow",
            module = this,
            toggled = true,
            category = "Text"
        )

        Slider(
            name = "Red",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Green",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Blue",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        /*
        ColorPicker(
            name = "Text Color",
            module = this,
            color = Color.WHITE,
            category = "Text"
        )
         */
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.render(run = {

            if(getSetting("Background").toggled) {
                skia.drawRoundedRect(x, y, getWidth(), getHeight(), getSetting("Corner Radius").currentSlider, skia.paint(getSetting("Background Color").color))
            }
        })

        mc.textRenderer.draw(getText(), getDescaledX() + getDescaledW() / 2f - mc.textRenderer.getStringWidth(getText()) / 2f, getDescaledY() + getDescaledH() / 2f - mc.textRenderer.fontHeight / 2f, Color(getSetting("Red").currentSlider / 255.0f, getSetting("Green").currentSlider / 255.0f, getSetting("Blue").currentSlider / 255.0f).rgb, getSetting("Text Shadow").toggled)
    }

    abstract fun getText(): String

    override fun getWidth(): Float {
        return 66f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    override fun getHeight(): Float {
        return 20f * Window(MinecraftClient.getInstance()).scaleFactor
    }
}