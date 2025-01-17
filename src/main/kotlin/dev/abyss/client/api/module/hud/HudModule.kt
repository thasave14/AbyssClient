package dev.abyss.client.api.module.hud

import dev.abyss.client.api.module.Module
import dev.abyss.client.skia.Skia
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.round

abstract class HudModule : Module() {

    var x: Float = 0f
    var y: Float = 0f

    abstract fun render(skia: Skia, x: Float, y: Float)

    fun renderDummy(skia: Skia, x: Float, y: Float) = render(skia, x, y)

    abstract fun getWidth(): Float

    abstract fun getHeight(): Float

    fun getDescaledX(): Float = getAbsoluteX() / Window(MinecraftClient.getInstance()).scaleFactor

    fun getDescaledY(): Float = getAbsoluteY() / Window(MinecraftClient.getInstance()).scaleFactor

    fun getDescaledW(): Float = getWidth() / Window(MinecraftClient.getInstance()).scaleFactor

    fun getDescaledH(): Float = getHeight() / Window(MinecraftClient.getInstance()).scaleFactor

    fun isHovered(): Boolean {

        var msX = Mouse.getX()
        var msY = Display.getHeight() - Mouse.getY()

        return (msX >= getAbsoluteX() && msX < getAbsoluteX() + getWidth()) && (msY >= getAbsoluteY() && msY < getAbsoluteY() + getHeight())
    }

    /**
     * One out of nine sections on the screen in which the HUD widget is placed.
     */
    var screenSection = ScreenSection.TOP_LEFT

    /**
     * A custom decimal format used when setting the relative coordinates.
     */
    private val decimalFormat = DecimalFormat("#.###", DecimalFormatSymbols(Locale.ENGLISH))

    /**
     * Calculates the actual x position of the HUD widget on the screen.
     */
    fun getAbsoluteX(): Float {
        val windowWidth = Display.getWidth().toFloat()

        val originOffset = round(windowWidth / 3.0 * this.x).toFloat()

        if (this.screenSection == ScreenSection.TOP_LEFT || this.screenSection == ScreenSection.CENTER_LEFT || this.screenSection == ScreenSection.BOTTOM_LEFT)
            return originOffset
        else if (this.screenSection == ScreenSection.TOP_CENTER || this.screenSection == ScreenSection.CENTER || this.screenSection == ScreenSection.BOTTOM_CENTER)
            return (round((windowWidth - this.getWidth()) / 2.0) + originOffset).toFloat()
        else
            return windowWidth - this.getWidth() - originOffset
    }

    /**
     * Calculates the actual y position of the HUD widget on the screen.
     */
    fun getAbsoluteY(): Float {
        val windowHeight = Display.getHeight().toFloat()

        val originOffset = round(windowHeight / 3.0 * this.y).toFloat()

        if (this.screenSection == ScreenSection.TOP_LEFT || this.screenSection == ScreenSection.TOP_CENTER || this.screenSection == ScreenSection.TOP_RIGHT)
            return originOffset
        else if (this.screenSection == ScreenSection.CENTER_LEFT || this.screenSection == ScreenSection.CENTER || this.screenSection == ScreenSection.CENTER_RIGHT)
            return (round((windowHeight - this.getHeight()) / 2.0) + originOffset).toFloat()
        else
            return windowHeight - this.getHeight() - originOffset
    }

    /**
     * Takes the absolute position on the screen and compiles it into relative coordinates and a screen section relative to the current window size.
     *
     * @param x The absolute x coordinate in pixels.
     * @param y The absolute y coordinate in pixels.
     */
    fun setAbsolutePosition(x: Float, y: Float) {

        val sectionWidth = Display.getWidth() / 3
        val sectionHeight = Display.getHeight() / 3

        // code could be improved I guess
        val relX: Double
        var relY: Double

        if (x < sectionWidth) {
            this.screenSection = ScreenSection.TOP_LEFT
            relX = x / sectionWidth.toDouble()
            relY = y / sectionHeight.toDouble()

            if (y > sectionHeight * 2) {
                this.screenSection = ScreenSection.BOTTOM_LEFT
                relY = (3 * sectionHeight - y - this.getHeight()).toDouble() / sectionHeight
            } else if (y > sectionHeight) {
                this.screenSection = ScreenSection.CENTER_LEFT
                relY = (y - (sectionHeight * 3 - this.getHeight()) / 2.0) / sectionHeight
            }
        } else if (x < sectionWidth * 2) {
            this.screenSection = ScreenSection.TOP_CENTER
            relX = (x - (sectionWidth * 3 - this.getWidth()) / 2.0) / sectionWidth
            relY = y / sectionHeight.toDouble()

            if (y > sectionHeight * 2) {
                this.screenSection = ScreenSection.BOTTOM_CENTER
                relY = (3 * sectionHeight - y - this.getHeight()).toDouble() / sectionHeight
            } else if (y > sectionHeight) {
                this.screenSection = ScreenSection.CENTER
                relY = (y - (sectionHeight * 3 - this.getHeight()) / 2.0) / sectionHeight
            }
        } else {
            this.screenSection = ScreenSection.TOP_RIGHT
            relX = (3 * sectionWidth - x - this.getWidth()).toDouble() / sectionWidth
            relY = y / sectionHeight.toDouble()

            if (y > sectionHeight * 2) {
                this.screenSection = ScreenSection.BOTTOM_RIGHT
                relY = (3 * sectionHeight - y - this.getHeight()).toDouble() / sectionHeight
            } else if (y > sectionHeight) {
                this.screenSection = ScreenSection.CENTER_RIGHT
                relY = (y - (sectionHeight * 3 - this.getHeight()) / 2.0) / sectionHeight
            }
        }

        this.x = decimalFormat.format(relX).toFloat()
        this.y = decimalFormat.format(relY).toFloat()
    }
}