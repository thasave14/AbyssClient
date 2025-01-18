package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.TextHudModule
import dev.abyss.client.utils.KDTracker

class KDModule : TextHudModule() {
    // TODO: Fix the fact that if the player isn't killed from another player, but for example he fell down the world, it doesn't count as a death, also non-player entities don't count
    override fun getText(): String {
        return "K/D: ${KDTracker.getKD()}"
    }

    override fun getMetaData(): MetaData {
        return MetaData("KD", "Display your Kills/Deaths", Type.HUD)
    }
}