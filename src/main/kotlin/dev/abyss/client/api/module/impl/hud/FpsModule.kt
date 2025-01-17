package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.TextHudModule

import net.minecraft.client.MinecraftClient

class FpsModule : TextHudModule() {

    override fun getText(): String {
        return "FPS: ${MinecraftClient.getCurrentFps()}"
    }

    override fun getMetaData(): MetaData {
        return MetaData("FPS", "Display your FPS", Type.HUD)
    }
}