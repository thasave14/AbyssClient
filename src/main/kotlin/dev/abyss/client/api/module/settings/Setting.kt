package dev.abyss.client.api.module.settings

import dev.abyss.client.api.module.Module
import org.lwjgl.input.Keyboard
import java.awt.Color

class Setting(val name: String, val module: Module?, val category: String) {

    var settingType: SettingType = SettingType.SWITCH

    @get:JvmName("isToggled")
    var toggled: Boolean = false

    var key: Int = Keyboard.KEY_NONE

    var minSlider: Float = 0f
    var currentSlider: Float = 0f
    var maxSlider: Float = 0f

    var modes: Array<String> = arrayOf("")
    var currentMode: String = ""

    var color: Color = Color(0, 0, 0, 0)

    var text: String = ""
    var placeHolder: String = ""

    constructor(name: String, module: Module?) : this(name, module, "General") {
        this.settingType = SettingType.DIVIDER
    }

    constructor(name: String, module: Module?, toggled: Boolean, category: String): this(name, module, category) {
        this.toggled = toggled
        this.settingType = SettingType.SWITCH
    }

    constructor(name: String, module: Module?, key: Int, category: String): this(name, module, category) {
        this.key = key
        this.settingType = SettingType.KEY
    }

    constructor(name: String, module: Module, minSlider: Float, currentSlider: Float, maxSlider: Float, category: String): this(name, module, category) {
        this.minSlider = minSlider
        this.currentSlider = currentSlider
        this.maxSlider = maxSlider
        this.settingType = SettingType.SLIDER
    }

    constructor(name: String, module: Module, modes: Array<String>, currentMode: String, category: String): this(name, module, category) {
        this.modes = modes
        this.currentMode = currentMode
        this.settingType = SettingType.MODE
    }

    constructor(name: String, module: Module, color: Color, category: String): this(name, module, category) {
        this.color = color
        this.settingType = SettingType.COLOR
    }

    constructor(name: String, module: Module, placeHolder: String, category: String): this(name, module, category) {
        this.placeHolder = placeHolder
        this.settingType = SettingType.TEXT
    }
}
