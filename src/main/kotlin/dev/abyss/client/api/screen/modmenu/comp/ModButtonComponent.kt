package dev.abyss.client.api.screen.modmenu.comp

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.Module
import dev.abyss.client.api.module.settings.SettingType
import dev.abyss.client.api.screen.modmenu.comp.settings.*
import dev.abyss.client.api.screen.modmenu.comp.settings.impl.*
import dev.abyss.client.api.screen.ui.component.UIComponent
import dev.abyss.client.skia.Skia
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.InputUtils
import dev.abyss.client.utils.animate.ColorAnimation
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.Window
import org.lwjgl.opengl.Display
import java.awt.Color

class ModButtonComponent(val module: Module, x: Float, y: Float) : UIComponent(x, y) {

    var settings: Boolean = false

    val hoverAnim = ColorAnimation()

    val settingComps = mutableListOf<SettingComponent>()

    val toggleAnim1 = ColorAnimation()
    val toggleAnim2 = ColorAnimation()

    init {

        initSettings()
    }

    fun initSettings() {

        settingComps.clear()

        var offsetY = (Display.getHeight() / 2f - (300f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f) + 150f

        for(setting in module.settings) {

            if(setting.settingType == SettingType.SWITCH) {
                val comp = SwitchComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settingComps.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.SLIDER) {
                val comp = SliderComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settingComps.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.KEY) {
                val comp = KeyComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settingComps.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.DIVIDER) {
                val comp = DividerComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settingComps.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.TEXT) {
                val comp = TextComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settingComps.add(comp)
                offsetY += comp.getHeight() + 10f
            }
        }
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        if(!Abyss.getInstance().modMenuScreen.modSettings) {

            skia.drawRoundedRect(x, y, getWidth(), getHeight(), 20f, skia.paint(Color(5, 5, 5)))

            val btnHeight = 20f * resolution.scaleFactor

            //if(module.toggled) Color(0, 111, 238) else Color(185 + 25, 50 + 25, 25 + 25)

            //if(module.toggled) Color(0, 86, 185) else Color(185, 50, 25

            val colors = arrayOf(
               toggleAnim1.animateAsState(module.toggled, Color(0, 111, 238), Color(185 + 25, 50 + 25, 25 * 2)), toggleAnim2.animateAsState(module.toggled, Color(0, 86, 185), Color(185, 50, 25))
            )

            skia.drawRoundedGradientRect(x + 10f, y + getHeight() - btnHeight - 10f, getWidth() - 20f, btnHeight, 15f, colors[0], colors[1])

            skia.drawText(module.getMetaData().name, Fonts.interRegular(30f), x + getWidth() / 2f - skia.getTextWidth(module.getMetaData().name, Fonts.interMedium(30f)) / 2f, y + getHeight() / 2f - skia.getTextHeight(module.getMetaData().name, Fonts.interRegular(30f)) / 2f + 20f, skia.paint(Color.WHITE))

            val text = if(module.toggled) "Enabled" else "Disabled"

            val btnX = x + 10f
            val btnY = y + getHeight() - btnHeight - 10f

            skia.drawText(text, Fonts.interRegular(25f), btnX + (getWidth() - 20f) / 2f - skia.getTextWidth(text, Fonts.interRegular(25f)) / 2f, btnY + btnHeight / 2f - skia.getTextHeight(text, Fonts.interRegular(25f)) / 2f, skia.paint(Color.WHITE))
        }

        if(settings) {

            val textHovered = InputUtils.isMouseOver(Abyss.getInstance().modMenuScreen.getX() + Abyss.getInstance().modMenuScreen.getSideBarWidth() + 40f, Abyss.getInstance().modMenuScreen.getY() + 35f, skia.getTextWidth("Modules", Fonts.interMedium(40f)), skia.getTextHeight("Modules", Fonts.interMedium(40f)))

            val textColor = hoverAnim.animateAsState(textHovered, Color(0, 86, 185), Color.WHITE)

            skia.drawText("Modules", Fonts.interMedium(40f), Abyss.getInstance().modMenuScreen.getX() + Abyss.getInstance().modMenuScreen.getSideBarWidth() + 40f, Abyss.getInstance().modMenuScreen.getY() + 35f, skia.paint(textColor))

            skia.drawText(" > ${module.getMetaData().name}", Fonts.interMedium(40f), (Abyss.getInstance().modMenuScreen.getX() + Abyss.getInstance().modMenuScreen.getSideBarWidth() + 40f) + skia.getTextWidth("Modules", Fonts.interMedium(40f)), Abyss.getInstance().modMenuScreen.getY() + 35f, skia.paint(Color.WHITE))

            skia.drawText(module.getMetaData().description, Fonts.interRegular(30f), Abyss.getInstance().modMenuScreen.getX() + Abyss.getInstance().modMenuScreen.getSideBarWidth() + 40f, (Abyss.getInstance().modMenuScreen.getY() + 35f) + skia.getTextHeight(module.getMetaData().name, Fonts.interMedium(40f)) + 5f, skia.paint(Color(200, 200, 200, 150)))

            skia.drawRect(Abyss.getInstance().modMenuScreen.getX() + Abyss.getInstance().modMenuScreen.getSideBarWidth() + 40f, (Abyss.getInstance().modMenuScreen.getY() + 35) + (skia.getTextHeight(module.getMetaData().name, Fonts.interMedium(40f)) + skia.getTextHeight(module.getMetaData().description, Fonts.interRegular(40f))) + 8f, (Abyss.getInstance().modMenuScreen.getMenuWidth() - Abyss.getInstance().modMenuScreen.getSideBarWidth()) - 60f, 1f, skia.paint(Color(25, 25, 25)))

            settingComps.forEach {
                it.render(skia, it.x, it.y)
            }
        }
    }

    fun update() {

    }

    override fun onClick(button: Int) {

        if(!Abyss.getInstance().modMenuScreen.modSettings) {

            if(InputUtils.isMouseOver(x + 10f, y + getHeight() - (20f * resolution.scaleFactor) - 10f, getWidth() - 20f, 20f * resolution.scaleFactor)) {
                if(button == 0) {
                    module.setToggled()
                }
            }

            if(isHovered()) {
                if(button == 1) {
                    settings = true
                    Abyss.getInstance().modMenuScreen.modSettings = true
                }
            }
        }

        if(settings) {

            val textHovered = InputUtils.isMouseOver(Abyss.getInstance().modMenuScreen.getX() + Abyss.getInstance().modMenuScreen.getSideBarWidth() + 40f, Abyss.getInstance().modMenuScreen.getY() + 35f, Abyss.getInstance().skia.getTextWidth("Modules", Fonts.interMedium(40f)), Abyss.getInstance().skia.getTextHeight("Modules", Fonts.interMedium(40f)))

            if(textHovered) {
                if(button == 0) {
                    settings = false
                    Abyss.getInstance().modMenuScreen.modSettings = false
                }
            }

            settingComps.forEach {
                it.onClick(button)
            }
        }
    }

    override fun onKey(char: Char, key: Int) {

        if(settings) {

            settingComps.forEach {
                it.onKey(char, key)
            }
        }
    }

    override fun onMouseRelease() {

        if(settings) {

            settingComps.forEach {
                it.onMouseRelease()
            }
        }
    }

    override fun getWidth(): Float {
        return 100f * resolution.scaleFactor
    }

    override fun getHeight(): Float {
        return 110f * resolution.scaleFactor
    }
}