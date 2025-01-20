package dev.abyss.client.api.module.impl.hud.togglesprint

import dev.abyss.client.Abyss
import net.minecraft.client.MinecraftClient

/**
 * @author EricGolde (I think)
 * Source: <li> https://github.com/ClientPlayground/Melon-Client/blob/beta-v4/src/main/java/me/kaimson/melonclient/features/modules/ToggleSprintModule.java <li/>
 * ported to kotlin by iLofiz (and put in a diff class)
 */
object ToggleSprintMovement {

    var sprintToggled: Boolean = false
    var wasPressed: Int = 0
    val keyHoldTicks = 7

    @JvmStatic
    fun update() {

        if(Abyss.getInstance().moduleRegistry.getModule("Toggle Sprint").toggled) {
            if(MinecraftClient.getInstance().options.sprintKey.isPressed) {
                if (this.wasPressed == 0) {
                    if (this.sprintToggled) {
                        this.wasPressed = -1
                    } else if (MinecraftClient.getInstance().player.abilities.flying) {
                        this.wasPressed = this.keyHoldTicks + 1
                    } else {
                        this.wasPressed = 1
                    }
                    this.sprintToggled = !this.sprintToggled
                } else if (this.wasPressed > 0) {
                    ++this.wasPressed
                }
            } else {
                if (this.keyHoldTicks > 0 && this.wasPressed > this.keyHoldTicks) {
                    this.sprintToggled = false
                }
                this.wasPressed = 0
            }
        } else {
            this.sprintToggled = false
        }
        if (this.sprintToggled) {
            MinecraftClient.getInstance().player.setSprinting(true)
        }
    }
}