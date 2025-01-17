package dev.abyss.client.skia

import dev.abyss.client.skija.SkiaRenderer
import dev.abyss.client.utils.InputUtils
import io.github.humbleui.skija.*
import io.github.humbleui.types.RRect
import io.github.humbleui.types.Rect
import org.lwjgl.input.Mouse
import java.awt.Color
import java.awt.Desktop
import java.net.URI
import java.net.URL

/**
 * @author iLofiz
 * The Skia Function Class
 */
class Skia {

    private fun getCanvas(): Canvas = SkiaRenderer.getCanvas()

    fun drawURL(url: URI, font: Font, x: Float, y: Float, color: Color) {

        var w = getTextWidth(url.toString(), font)
        var h = getTextHeight(url.toString(), font)

        drawText(url.toString(), font, x, y, paint( if(InputUtils.isMouseOver(x, y, w, h)) Color(0, 86, 185) else color ))

        if(Mouse.getEventButtonState()) {

            if(InputUtils.isMouseOver(x, y, w, h)) {
                if(Mouse.getEventButton() == 0) {
                    Desktop.getDesktop().browse(url)
                }
            }
        }
    }

    fun scissor(x: Float, y: Float, w: Float, h: Float, runnable: Runnable) {
        getCanvas().save()
        getCanvas().clipRect(Rect.makeXYWH(x, y, w, h))
        runnable.run()
        getCanvas().restore()
    }

    fun scissor(x: Float, y: Float, w: Float, h: Float, radius: Float, runnable: Runnable) {
        getCanvas().save()
        getCanvas().clipRRect(RRect.makeXYWH(x, y, w, h, radius))
        runnable.run()
        getCanvas().restore()
    }

    fun drawCircle(x: Float, y: Float, size: Float, paint: Paint) {
        getCanvas().drawCircle(x, y, size, paint)
    }

    fun drawRect(x: Float, y: Float, w: Float, h: Float, paint: Paint) {
        getCanvas().drawRect(Rect.makeXYWH(x, y, w, h), paint)
    }

    fun drawRoundedRect(x: Float, y: Float, w: Float, h: Float, radius: Float, paint: Paint) {
        getCanvas().drawRRect(RRect.makeXYWH(x, y, w, h, radius), paint)
    }

    fun drawRoundedOutline(x: Float, y: Float, w: Float, h: Float, radius: Float, outline: Float, paint: Paint) {
        paint.setStrokeWidth(outline)
        paint.setStroke(true)
        getCanvas().drawRRect(RRect.makeXYWH(x, y, w, h, radius), paint)
    }

    fun drawRectOutline(x: Float, y: Float, w: Float, h: Float, outline: Float, paint: Paint) {
        paint.setStrokeWidth(outline)
        paint.setStroke(true)
        getCanvas().drawRect(Rect.makeXYWH(x, y, w, h), paint)
    }

    fun drawRoundedGradientRect(x: Float, y: Float, w: Float, h: Float, radius: Float, start: Color, end: Color) {

        val colors = intArrayOf(
            colorToArgb(start),
            colorToArgb(end)
        )

        val positions = floatArrayOf(
            0.0f,
            1.0f
        )

        val gradient = Shader.makeLinearGradient(x, y, x, y + h, colors, positions, GradientStyle.DEFAULT)
        val paint = Paint().setShader(gradient)
        getCanvas().drawRRect(RRect.makeXYWH(x, y, w, h, radius), paint)
    }

    fun drawText(text: String, font: Font, x: Float, y: Float, paint: Paint) {
        val bounds = font.measureText(text)
        getCanvas().drawString(text, x - 2f, (y - 1f) + bounds.height, font, paint)
    }

    fun drawImage(image: Image, x: Float, y: Float, w: Float, h: Float, radius: Float) {
        getCanvas().drawImageRect(image, RRect.makeXYWH(x, y, w, h, radius))
    }

    fun getTextWidth(text: String, font: Font): Float {
        return font.measureTextWidth(text)
    }

    fun getTextHeight(text: String, font: Font): Float {
        val bounds = font.measureText(text)
        return bounds.height
    }

    fun paint(color: Color): Paint {
        return Paint().setARGB(color.alpha, color.red, color.green, color.blue)
    }

    fun render(run: Runnable) {
        SkiaRenderer.begin()
        run.run()
        SkiaRenderer.end()
    }

    private fun colorToArgb(color: Color): Int {
        return (color.alpha shl 24) or (color.red shl 16) or (color.green shl 8) or color.blue
    }
}