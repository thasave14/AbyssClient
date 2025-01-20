package dev.abyss.client.api.module.impl.hud.keystrokes

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.impl.hud.keystrokes.key.Key
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.Slider
import dev.abyss.client.api.module.settings.Switch
import dev.abyss.client.api.module.settings.TextBox
import dev.abyss.client.skia.Skia
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window
import java.awt.Color

class KeystrokesModule : HudModule() {

    val W = Key()
    val A = Key()
    val S = Key()
    val D = Key()
    val SPACE = Key()

    init {

        CategoryDivider(
            name = "General ",
            module = this
        )

        Slider(
            name = "Fade Amount",
            module = this,
            minSlider = 6f,
            currentSlider = 6f,
            maxSlider = 15f,
            category = "General"
        )

        CategoryDivider(
            name = "Background ",
            module = this
        )

        Slider(
            name = "Background Red",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Background Green",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Background Blue",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Background (Pressed) Red",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Background (Pressed) Green",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Background (Pressed) Blue",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Switch(
            name = "Background",
            module = this,
            toggled = true,
            "Background"
        )

        Slider(
            name = "Corner Radius",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 20f,
            category = "Background"
        )

        CategoryDivider(
            name = "Text ",
            module = this
        )

        Switch(
            name = "Text Shadow",
            module = this,
            toggled = true,
            category = "Text"
        )

        Slider(
            name = "Text Red",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Text Green",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Text Blue",
            module = this,
            minSlider = 0f,
            currentSlider = 255f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Text (Pressed) Red",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Text (Pressed) Green",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 255f,
            category = "Text"
        )

        Slider(
            name = "Text (Pressed) Blue",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 255f,
            category = "Text"
        )
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        val wasdBtnSize = 20f * Window(mc).scaleFactor

        var bgColor = Color(getSetting("Background Red").color.red / 255.0f, getSetting("Background Green").color.green / 255.0f, getSetting("Background Blue").color.blue / 255.0f, 90f / 255.0f)
        var bgColorPressed = Color(getSetting("Background (Pressed) Red").color.red / 255.0f, getSetting("Background (Pressed) Green").color.green / 255.0f, getSetting("Background (Pressed) Blue").color.blue / 255.0f, 90f / 255.0f)

        var textColor = Color(getSetting("Text Red").color.red / 255.0f, getSetting("Text Green").color.green / 255.0f, getSetting("Text Blue").color.blue / 255.0f)
        var textColorPressed = Color(getSetting("Text (Pressed) Red").color.red / 255.0f, getSetting("Text (Pressed) Green").color.green / 255.0f, getSetting("Text (Pressed) Blue").color.blue / 255.0f)

        W.render(x + getWidth() / 2f - wasdBtnSize / 2f, y, wasdBtnSize, wasdBtnSize, getSetting("Corner Radius").currentSlider, getSetting("Background").toggled, getSetting("Text Shadow").toggled, bgColor, bgColorPressed, textColor, textColorPressed, mc.options.forwardKey, getSetting("Fade Amount").currentSlider.toDouble())
        A.render(x, y + wasdBtnSize + 7, wasdBtnSize, wasdBtnSize, getSetting("Corner Radius").currentSlider, getSetting("Background").toggled, getSetting("Text Shadow").toggled, bgColor, bgColorPressed, textColor, textColorPressed, mc.options.leftKey, getSetting("Fade Amount").currentSlider.toDouble())
        S.render(x + getWidth() / 2f - wasdBtnSize / 2f, y + wasdBtnSize + 7, wasdBtnSize, wasdBtnSize, getSetting("Corner Radius").currentSlider, getSetting("Background").toggled, getSetting("Text Shadow").toggled, bgColor, bgColorPressed, textColor, textColorPressed, mc.options.backKey, getSetting("Fade Amount").currentSlider.toDouble())
        D.render((x + getWidth() / 2f - wasdBtnSize / 2f) + (wasdBtnSize + 7), y + wasdBtnSize + 7, wasdBtnSize, wasdBtnSize, getSetting("Corner Radius").currentSlider, getSetting("Background").toggled, getSetting("Text Shadow").toggled, bgColor, bgColorPressed, textColor, textColorPressed, mc.options.rightKey, getSetting("Fade Amount").currentSlider.toDouble())

        SPACE.render(x, y + ((wasdBtnSize * 2) + 14), getWidth() - 1, wasdBtnSize, getSetting("Corner Radius").currentSlider, getSetting("Background").toggled, getSetting("Text Shadow").toggled, bgColor, bgColorPressed, textColor, textColorPressed, mc.options.jumpKey, getSetting("Fade Amount").currentSlider.toDouble())
    }

    override fun getWidth(): Float {
        return 66f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    override fun getHeight(): Float {
        return 66f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    override fun getMetaData(): MetaData {
        return MetaData("Keystrokes", "Display your key presses", Type.HUD)
    }
}