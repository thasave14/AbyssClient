package dev.abyss.client.api.screen.modmenu.comp.settings

import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.api.screen.ui.component.UIComponent
import dev.abyss.client.skia.Skia
import java.awt.Color

abstract class SettingComponent(val setting: Setting, x: Float, y: Float) : UIComponent(x, y) {

}