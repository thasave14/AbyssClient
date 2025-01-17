package dev.abyss.client.api.screen

import dev.abyss.client.utils.Panorama

class MainMenuScreen : Panorama() {

    override fun tick() {
        super.tick()
        updateScreen()
    }
}