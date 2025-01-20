package dev.abyss.client.api.screen

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.screen.ui.component.impl.GeneralButton
import dev.abyss.client.utils.accessor.IMixinGameRenderer
import dev.abyss.client.utils.MathUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.Window
import net.minecraft.util.Identifier
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display
import java.awt.Color

class HudEditorScreen : Screen() {

    val mods = Abyss.getInstance().moduleRegistry.getEnabledHudModules()

    var selectedMod: HudModule? = null

    var lastX: Float = 0f
    var lastY: Float = 0f

    val button = GeneralButton(
        "Modules",
        Display.getWidth() / 2f - (100f * Window(MinecraftClient.getInstance()).scaleFactor),
        Display.getHeight() / 2f - (30f * Window(MinecraftClient.getInstance()).scaleFactor),
        clicked = {
            MinecraftClient.getInstance().setScreen(Abyss.getInstance().modMenuScreen)
        }
    )

    override fun render(mouseX: Int, mouseY: Int, tickDelta: Float) {

        val skia = Abyss.getInstance().skia

        button.update(Display.getWidth() / 2f - button.getWidth() / 2f, Display.getHeight() / 2f - button.getHeight() / 2f)

        skia.render {
            skia.drawRect(0f, 0f, Display.getWidth().toFloat(), Display.getHeight().toFloat(), skia.paint(Color(0, 0, 0, 60)))

            if(selectedMod != null) {

                var x = selectedMod!!.getAbsoluteX()
                var y = selectedMod!!.getAbsoluteY()

                var w = selectedMod!!.getWidth()

                var h = selectedMod!!.getHeight()

                var topLeft = MathUtils.isInArea(x, y, w, h, 0f, 0f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f)
                var topRight = MathUtils.isInArea(x, y, w, h, Display.getWidth().toFloat() / 2f, 0f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f)

                var bottomLeft = MathUtils.isInArea(x, y, w, h, 0f, Display.getHeight().toFloat() / 2f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f)
                var bottomRight = MathUtils.isInArea(x, y, w, h, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f)

                if(topLeft) {
                    skia.drawRect(0f, 0f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f, skia.paint(Color(0, 0, 0, 30)))
                }

                if(topRight) {
                    skia.drawRect(Display.getWidth().toFloat() / 2f, 0f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f, skia.paint(Color(0, 0, 0, 30)))
                }

                if(bottomLeft) {
                    skia.drawRect(0f, Display.getHeight().toFloat() / 2f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f, skia.paint(Color(0, 0, 0, 30)))
                }

                if(bottomRight) {
                    skia.drawRect(Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f, Display.getWidth().toFloat() / 2f, Display.getHeight().toFloat() / 2f, skia.paint(Color(0, 0, 0, 30)))
                }
            }

            button.render(skia, button.x, button.y)
        }

        for(mod in mods) {

            skia.drawRect(mod.getAbsoluteX(), mod.getAbsoluteY(), mod.getWidth(), mod.getHeight(), skia.paint(Color(25, 25, 25, 60)))

            mod.renderDummy(skia, mod.getAbsoluteX(), mod.getAbsoluteY())

            skia.drawRoundedOutline(mod.getAbsoluteX(), mod.getAbsoluteY(), mod.getWidth(), mod.getHeight(), 2f, 0.6f, skia.paint(Color(25, 25, 25)))

            if(mod.isHovered()) {

                skia.render {
                    skia.drawRoundedOutline(mod.getAbsoluteX(), mod.getAbsoluteY(), mod.getWidth(), mod.getHeight(), 2f, 1.5f, skia.paint(Color.WHITE))
                }
            }
        }

        if(selectedMod != null) {
            selectedMod!!.setAbsolutePosition(Mouse.getX() - lastX, (Display.getHeight() - Mouse.getY()) - lastY)
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {

        for(mod in mods) {
            if(mod.isHovered()) {
                if(button == 0) {
                    selectedMod = mod
                    lastX = Mouse.getX() - mod.getAbsoluteX()
                    lastY = (Display.getHeight() - Mouse.getY()) - mod.getAbsoluteY()
                    break
                }
            }
        }

        this.button.onClick(button)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, button: Int) {
        selectedMod = null
    }
}