package dev.abyss.client.utils

import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display

object InputUtils {

    fun isMouseOver(x: Float, y: Float, w: Float, h: Float): Boolean {

        var mouseX = Mouse.getX()
        var mouseY = Display.getHeight() - Mouse.getY()

        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h
    }
}