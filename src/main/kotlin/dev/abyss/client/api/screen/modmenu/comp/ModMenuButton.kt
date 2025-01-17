package dev.abyss.client.api.screen.modmenu.comp

import dev.abyss.client.api.screen.ui.component.UIComponent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.animate.ColorAnimation
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window
import java.awt.Color

class ModMenuButton(val iconId: String, x: Float, y: Float, val onClick: Runnable) : UIComponent(x, y) {

    val hoverAnim = ColorAnimation()

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.drawRoundedRect(x, y, getSize(), getSize(), 25f, skia.paint(hoverAnim.animateAsState(isHovered(), Color(200, 200, 200, 80), Color(0, 0, 0, 0))))

        skia.drawText(iconId, Fonts.icons(35f), x + getSize() / 2f - skia.getTextWidth(iconId, Fonts.icons(35f)) / 2f, y + getSize() / 2f - skia.getTextHeight(iconId, Fonts.icons(35f)) / 2f, skia.paint(Color.WHITE))
    }

    override fun onClick(button: Int) {

        if(isHovered()) {
            if(button == 0) {
                onClick.run()
            }
        }
    }

    override fun getWidth(): Float {
        return getSize()
    }

    override fun getHeight(): Float {
        return getSize()
    }

    fun getSize(): Float {
        return 30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f
    }
}