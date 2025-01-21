package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.TextHudModule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ClockModule : TextHudModule() {

    override fun getText(): String {

        val dtf = DateTimeFormatter.ofPattern("HH:mm a")
        val now = LocalDateTime.now()

        return dtf.format(now)
    }

    override fun getMetaData(): MetaData {
        return MetaData("Clock", "Display your IRL Time", Type.HUD)
    }
}