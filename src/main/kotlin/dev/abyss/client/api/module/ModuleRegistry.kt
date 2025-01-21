package dev.abyss.client.api.module

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.impl.OldAnimationsModule
import dev.abyss.client.api.module.impl.hud.*
import dev.abyss.client.api.module.impl.hud.keystrokes.KeystrokesModule
import dev.abyss.client.api.module.impl.hud.togglesprint.ToggleSprintModule
import dev.abyss.client.api.screen.HudEditorScreen
import dev.abyss.client.event.EventManager
import dev.abyss.client.event.Subscribe
import dev.abyss.client.event.impl.Render2DEvent
import net.minecraft.client.MinecraftClient

class ModuleRegistry {

    val modules = mutableListOf<Module>()

    init {
        EventManager.register(this)

        modules.add(FpsModule())
        modules.add(CpsModule())
        modules.add(PingModule())
        modules.add(ClockModule())
        modules.add(KeystrokesModule())
        modules.add(OldAnimationsModule())
        modules.add(KDModule())
        modules.add(ToggleSprintModule())
        modules.add(ResourcePackModule())
    }

    fun getEnabledHudModules(): MutableList<HudModule> {

        val hudModules = mutableListOf<HudModule>()

        for(mod in modules) {
            if(mod is HudModule && mod.toggled) {
                hudModules.add(mod)
            }
        }

        return hudModules
    }

    fun getModule(name: String): Module {
        return modules.stream().filter { it.getMetaData().name == name }.findFirst().orElse(null)
    }

    @Subscribe
    fun onRender(event: Render2DEvent) {

        for(module in getEnabledHudModules()) {
            if(MinecraftClient.getInstance().currentScreen !is HudEditorScreen) {
                module.render(Abyss.getInstance().skia, module.getAbsoluteX(), module.getAbsoluteY())
            }
        }
    }
}