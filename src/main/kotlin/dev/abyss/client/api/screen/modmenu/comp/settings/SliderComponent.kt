package dev.abyss.client.api.screen.modmenu.comp.settings

import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.InputUtils
import dev.abyss.client.utils.MathUtils
import dev.abyss.client.utils.animate.ColorAnimation
import dev.abyss.client.utils.animate.SimpleAnimation
import org.lwjgl.input.Mouse
import java.awt.Color

class SliderComponent(setting: Setting, x: Float, y: Float) : SettingComponent(setting, x, y) {

    val outline = ColorAnimation()

    private var width = 0.0
    private var height = 0.0

    private var baseWidth = 0.0
    private var valueWidth = 0.0
    private var dragging = false

    private val animation = SimpleAnimation()
    private val draggingAnimation = SimpleAnimation()

    init {
        this.width = getWidth().toDouble()
        this.height = getHeight().toDouble()
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getWidth(), getHeight(), 60f, skia.paint(Color(5, 5, 5)))

        skia.drawRoundedOutline(x, y, getWidth(), getHeight(), 60f, 1.4f, skia.paint( outline.animateAsState(isHovered(), Color.WHITE, Color(0, 0, 0, 0)) ))

        skia.drawText(setting.name, Fonts.interRegular(30f), x + getWidth() + 15f, y + getHeight() / 2f - skia.getTextHeight(setting.name, Fonts.interRegular(30f)) / 2f, skia.paint(
            Color.WHITE))

        val min = setting.minSlider
        val max = setting.maxSlider
        var current = setting.currentSlider

        baseWidth = width * (max - min) / (max - min)

        valueWidth = width * (current - min) / (max - min)

        var diff = Math.min(width.toFloat(), Math.max(0f, Mouse.getX() - (x - 1.5f))).toDouble()

        if(dragging) {

            if(diff == 0.0) {
                setting.currentSlider = min
            } else {
                setting.currentSlider = MathUtils.roundToPlace(((diff / width) * (max - min) + min), 2).toFloat()
            }
        }

        animation.setAnimation(valueWidth.toFloat(), 16.0)
        draggingAnimation.setAnimation(
            if (InputUtils.isMouseOver(
                    this.x - 6,
                    this.y - 3, baseWidth.toFloat() + 12f, height.toFloat()
                )
            ) 1.0f else 0.0f, 16.0
        )

        skia.scissor(x, y, getWidth(), getHeight(), 60f, runnable = {
            skia.drawRoundedGradientRect(x, y, animation.value, getHeight(), 60f, Color(0, 111, 238), Color(0, 86, 185))
        })

        skia.drawText("$current", Fonts.interRegular(25f), x + 20f, y + getHeight() / 2f - skia.getTextHeight("$current", Fonts.interRegular(25f)) / 2f, skia.paint(Color.WHITE))
    }

    override fun onClick(button: Int) {

        if(InputUtils.isMouseOver(x - 6, y - 3, baseWidth.toFloat() + 12, height.toFloat())) {
            if(button == 0) {
                dragging = true
            }
        }
    }

    override fun onMouseRelease() {
        dragging = false
    }

    override fun getWidth(): Float {
        return 100f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 15f * resolution.scaleFactor
    }
}