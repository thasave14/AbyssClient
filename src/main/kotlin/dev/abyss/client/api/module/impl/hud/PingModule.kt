package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.TextHudModule

class PingModule : TextHudModule() {

    override fun getText(): String {
        return "${getPing()} ms"
    }

    override fun getMetaData(): MetaData {
        return MetaData("Ping", "Display your Ping", Type.HUD)
    }

    private fun getPing(): Long {
        return if(mc.currentServerEntry != null) mc.currentServerEntry.ping else -1
    }
}