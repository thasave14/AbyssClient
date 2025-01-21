package dev.abyss.client.api.module.impl.hud.togglesprint

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.Switch
import dev.abyss.client.skia.Skia
import net.minecraft.client.util.Window
import java.awt.Color

class ToggleSprintModule : HudModule() {

    init {

        CategoryDivider(
            name = "Background ",
            module = this
        )

        Switch(
            name = "Background",
            module = this,
            toggled = false,
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

        Switch(
            name = "Brackets",
            module = this,
            toggled = true,
            category = "Text"
        )
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        skia.render {

            if(getSetting("Background").toggled) {
                skia.drawRect(x, y, getWidth(), getHeight(), skia.paint(Color(0, 0, 0, 90)))
            }
        }

        mc.textRenderer.draw(getFormattedText(), getDescaledX(), getDescaledY(), Color.WHITE.rgb, getSetting("Text Shadow").toggled)
    }

    override fun getWidth(): Float {
        return mc.textRenderer.getStringWidth(getFormattedText()).toFloat() * Window(mc).scaleFactor
    }

    override fun getHeight(): Float {
        return mc.textRenderer.fontHeight.toFloat() * Window(mc).scaleFactor
    }

    override fun getMetaData(): MetaData {
        return MetaData("Toggle Sprint", "Toggle Your Sprint", Type.HUD)
    }

    private fun getFormattedText(): String {
        return ( if(getSetting("Brackets").toggled) "[" else "" ) + getText() + ( if(getSetting("Brackets").toggled) "]" else "" )
    }

    private fun getText(): String {

        var displayText = ""

        if(mc.player.abilities == null) {
            displayText = ""
            return displayText
        }

        val isFlying = mc.player.abilities.flying
        val isSprintHeld = mc.options.sprintKey.isPressed
        val isSprinting = mc.player.isSprinting

        if (isFlying) {
            displayText = "Flying"
        } else if (this.toggled) {
            displayText = if (isSprintHeld) {
                "Sprinting (Key Held)"
            } else {
                "Sprinting (Toggled)"
            }
        } else if (isSprinting) {
            displayText = "Sprinting (Vanilla)"
        }

        return displayText
    }
}