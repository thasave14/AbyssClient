package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.TextHudModule
import dev.abyss.client.api.module.hud.TitleHudModule

import net.minecraft.client.MinecraftClient

class FpsModule : TitleHudModule() {

    override fun getMainText(): String {
        return "${MinecraftClient.getCurrentFps()}"
    }

    override fun getTitle(): String {
        return "FPS"
    }

    override fun getMetaData(): MetaData {
        return MetaData("FPS", "Display your FPS", Type.HUD)
    }
}