package dev.abyss.client.api.config

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.settings.Setting
import dev.abyss.client.api.module.settings.SettingType
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

//chatgpt config :sob:
object Config {

    private val LOGGER: Logger = Logger.getLogger(Config::class.java.name)
    private const val CONFIG_PATH = "config.json"

    @JvmStatic
    fun save() {
        val rootJson = JSONObject()
        rootJson.put("modules", serializeModules())
        rootJson.put("settings", serializeSettings(Abyss.getInstance().settingsManager.settings))

        writeFile(rootJson.toString(4))
    }

    private fun serializeModules(): JSONObject {
        val modulesJson = JSONObject()

        for (module in Abyss.getInstance().moduleRegistry.modules) {
            val moduleJson = JSONObject()
            moduleJson.put("enabled", module.toggled)

            if (module is HudModule && module.getMetaData().type == Type.HUD) {
                val hud = module as HudModule
                moduleJson.put("x", hud.getAbsoluteX())
                moduleJson.put("y", hud.getAbsoluteY())
            }

            moduleJson.put("settings", serializeSettings(module.settings))
            modulesJson.put(module.getMetaData().name, moduleJson)
        }

        return modulesJson
    }

    private fun serializeSettings(settings: Iterable<Setting>): JSONObject {
        val settingsJson = JSONObject()

        for (setting in settings) {
            val key = setting.name.lowercase(Locale.getDefault())
            when (setting.settingType) {
                SettingType.SWITCH -> settingsJson.put("$key-enabled", setting.toggled)
                SettingType.KEY -> settingsJson.put("$key-key", setting.key)
                SettingType.MODE -> settingsJson.put("$key-current", setting.currentMode)
                SettingType.DIVIDER -> continue
                SettingType.SLIDER -> settingsJson.put("$key-currentSlider", setting.currentSlider)
                SettingType.COLOR -> continue
                SettingType.TEXT -> continue
            }
        }

        return settingsJson
    }

    @JvmStatic
    fun load() {
        val configFile = File(CONFIG_PATH)
        if (!configFile.exists()) {
            LOGGER.warning("Config file not found, loading defaults.")
            return
        }

        try {
            FileReader(configFile).use { reader ->
                val rootJson = JSONObject(JSONTokener(reader))
                loadModules(rootJson.optJSONObject("modules"))
                loadSettings(rootJson.optJSONObject("settings"), Abyss.getInstance().settingsManager.settings)
            }
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, "Error reading config file", e)
        }
    }

    private fun loadModules(modulesJson: JSONObject?) {
        if (modulesJson == null) return

        for (module in Abyss.getInstance().moduleRegistry.modules) {
            val moduleName = module.getMetaData().name

            if (modulesJson.has(moduleName)) {
                val moduleJson = modulesJson.getJSONObject(moduleName)
                module.toggled = moduleJson.optBoolean("enabled", module.toggled)

                if (module is HudModule && module.getMetaData().type == Type.HUD) {
                    val hud = module as HudModule
                    hud.setAbsolutePosition(
                        moduleJson.optDouble("x", hud.getAbsoluteX().toDouble()).toFloat(),
                        moduleJson.optDouble("y", hud.getAbsoluteY().toDouble()).toFloat()
                    )
                }

                loadSettings(moduleJson.optJSONObject("settings"), module.settings)
            }
        }
    }

    private fun loadSettings(settingsJson: JSONObject?, settings: Iterable<Setting>) {
        if (settingsJson == null) return

        for (setting in settings) {
            val key = setting.name.lowercase(Locale.getDefault())
            when (setting.settingType) {
                SettingType.SWITCH -> setting.toggled = settingsJson.optBoolean("$key-enabled", setting.toggled)
                SettingType.KEY -> setting.key = settingsJson.optInt("$key-key", setting.key)
                SettingType.MODE -> setting.currentMode = settingsJson.optString("$key-current", setting.currentMode)
                SettingType.DIVIDER -> continue
                SettingType.SLIDER -> setting.currentSlider = settingsJson.optFloat("$key-currentSlider", setting.currentSlider)
                SettingType.COLOR -> continue
                SettingType.TEXT -> continue
            }
        }
    }

    private fun writeFile(content: String) {
        try {
            FileWriter(CONFIG_PATH).use { writer ->
                writer.write(content)
            }
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, "Error writing config file", e)
        }
    }
}