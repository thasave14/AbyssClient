package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.TextHudModule
import dev.abyss.client.api.module.hud.TitleHudModule
import dev.abyss.client.utils.KDTracker

class KDModule : TitleHudModule() {

    // TODO: Fix the fact that if the player isn't killed from another player, for example he fell into the void, it doesn't count as a death, also non-player entities don't count
    // TODO: (for save) fix ur fucking english mate

    override fun getMainText(): String {
        return "${KDTracker.getKD()}"
    }

    override fun getTitle(): String {
        return "K/D"
    }

    override fun getMetaData(): MetaData {
        return MetaData("KD", "Display your Kills/Deaths", Type.HUD)
    }
}