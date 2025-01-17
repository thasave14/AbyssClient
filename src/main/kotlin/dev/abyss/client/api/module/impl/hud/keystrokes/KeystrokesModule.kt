package dev.abyss.client.api.module.impl.hud.keystrokes

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.settings.TextBox
import dev.abyss.client.skia.Skia
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window

class KeystrokesModule : HudModule() {

    init {

    }

    override fun render(skia: Skia, x: Float, y: Float) {

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