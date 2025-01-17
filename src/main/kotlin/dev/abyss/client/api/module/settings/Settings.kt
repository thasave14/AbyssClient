package dev.abyss.client.api.module.settings

import dev.abyss.client.api.module.Module
import java.awt.Color

fun Switch(name: String, module: Module, toggled: Boolean, category: String) {
    module.settings.add(Setting(name, module, toggled, category))
}

fun ColorPicker(name: String, module: Module, color: Color, category: String) {
    module.settings.add(Setting(name, module, color, category))
}

fun Slider(name: String, module: Module, minSlider: Float, currentSlider: Float, maxSlider: Float, category: String) {
    module.settings.add(Setting(name, module, minSlider, currentSlider, maxSlider, category))
}

fun KeyBind(name: String, module: Module, key: Int, category: String) {
    module.settings.add(Setting(name, module, key, category))
}

fun ModePicker(name: String, module: Module, modes: Array<String>, currentMode: String, category: String) {
    module.settings.add(Setting(name, module, modes, currentMode, category))
}

fun TextBox(name: String, module: Module, placeHolder: String, category: String) {
    module.settings.add(Setting(name, module, placeHolder, category))
}

fun CategoryDivider(name: String, module: Module) {
    module.settings.add(Setting(name, module))
}