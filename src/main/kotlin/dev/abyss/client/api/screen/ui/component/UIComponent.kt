package dev.abyss.client.api.screen.ui.component

import dev.abyss.client.skia.Skia
import dev.abyss.client.utils.InputUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window

abstract class UIComponent(var x: Float, var y: Float) {

    var resolution = Window(MinecraftClient.getInstance())

    abstract fun render(skia: Skia, x: Float, y: Float)

    open fun onClick(button: Int) {}

    open fun onMouseRelease() {}

    open fun onKey(char: Char, key: Int) {}

    abstract fun getWidth(): Float

    abstract fun getHeight(): Float

    fun update(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun isHovered(): Boolean {
        return InputUtils.isMouseOver(x, y, getWidth(), getHeight())
    }

    open fun onResize() {}
}