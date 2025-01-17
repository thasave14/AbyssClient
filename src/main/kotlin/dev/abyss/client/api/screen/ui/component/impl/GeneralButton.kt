package dev.abyss.client.api.screen.ui.component.impl

import dev.abyss.client.api.screen.ui.component.UIComponent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.ColorAnimation
import java.awt.Color

class GeneralButton(private val text: String, x: Float, y: Float, val clicked: Runnable) : UIComponent(x, y) {

    val hoverAnim = ColorAnimation()
    val textHoverAnim = ColorAnimation()

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getWidth(), getHeight(), 14f, skia.paint( hoverAnim.animateAsState(isHovered(), Color(45, 45, 45, 110), Color(15, 15, 15, 110), 15.0) ))
        skia.drawRoundedOutline(x, y, getWidth(), getHeight(), 14f, 2f, skia.paint(Color(255, 255, 255, 220)))

        skia.drawText(text, Fonts.interRegular(30f), x + getWidth() / 2f - skia.getTextWidth(text, Fonts.interRegular(30f)) / 2f, y + getHeight() / 2f - skia.getTextHeight(text, Fonts.interRegular(30f)) / 2f, skia.paint( textHoverAnim.animateAsState(isHovered(), Color.WHITE, Color(200, 200, 200, 170)) ))
    }

    override fun onClick(button: Int) {

        if(isHovered()) {
            if(button == 0) {
                clicked.run()
            }
        }
    }

    override fun getWidth(): Float {
        return 100f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 30f * resolution.scaleFactor
    }
}