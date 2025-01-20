package dev.abyss.client.api.screen.modmenu

import dev.abyss.client.Abyss
import dev.abyss.client.api.module.settings.SettingType
import dev.abyss.client.api.screen.HudEditorScreen
import dev.abyss.client.api.screen.modmenu.comp.ModButtonComponent
import dev.abyss.client.api.screen.modmenu.comp.ModMenuButton
import dev.abyss.client.api.screen.modmenu.comp.PageButtonComponent
import dev.abyss.client.api.screen.modmenu.comp.settings.*
import dev.abyss.client.api.screen.ui.component.UIComponent
import dev.abyss.client.skia.font.Fonts
import dev.abyss.client.utils.InputUtils
import dev.abyss.client.utils.animate.SimpleAnimation
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.Window
import org.lwjgl.opengl.Display
import java.awt.Color
import java.net.URI
import java.net.URL

class ModMenuScreen : Screen() {

    var currentPage: Page = Page.MODULES

    val pages = mutableListOf<PageButtonComponent>()

    val mods = mutableListOf<ModButtonComponent>()

    val settings = mutableListOf<SettingComponent>()

    var animatePage = SimpleAnimation()

    var previousPage: PageButtonComponent

    var modSettings: Boolean = false

    val modMenuButtons = mutableListOf<UIComponent>()

    val pageTitleAnimation = SimpleAnimation()

    init {

        initPages()
        previousPage = pages[0]
        initMods()
    }

    override fun init() {
        super.init()

        modMenuButtons.clear()
        modMenuButtons.add(ModMenuButton(
            "h",
            (getX() + 10f) + getSideBarWidth() / 2f - (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f) / 2f,
            getY() + getMenuHeight() - (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f) - 15f,
            onClick = {
                MinecraftClient.getInstance().setScreen(HudEditorScreen())
            }
        ))

        pageTitleAnimation.value = getX()

        initPages()
        initMods()
        initSettings()
    }

    fun initSettings() {

        settings.clear()

        var offsetY = (Display.getHeight() / 2f - (300f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f) + 150f

        for(setting in Abyss.getInstance().settingsManager.settings) {

            if(setting.settingType == SettingType.SWITCH) {
                val comp = SwitchComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settings.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.SLIDER) {
                val comp = SliderComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settings.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.KEY) {
                val comp = KeyComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settings.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.DIVIDER) {
                val comp = DividerComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settings.add(comp)
                offsetY += comp.getHeight() + 10f
            }

            if(setting.settingType == SettingType.TEXT) {
                val comp = TextComponent(setting, Display.getWidth() / 2f - (460f * Window(MinecraftClient.getInstance()).scaleFactor) / 2f + (30f * Window(MinecraftClient.getInstance()).scaleFactor) + 40f, offsetY)
                settings.add(comp)
                offsetY += comp.getHeight() + 10f
            }
        }
    }

    fun initPages() {

        pages.clear()

        var offsetY = getY() + 50f

        for(page in Page.entries) {
            val comp = PageButtonComponent(page, (getX() + 10f) + getSideBarWidth() / 2f - (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f) / 2f, offsetY)
            pages.add(comp)
            offsetY += comp.getSize() + 10f
        }

        previousPage = pages[0]
    }

    fun initMods() {

        modSettings = false

        mods.clear()

        var offsetX = getX() + getSideBarWidth() + 25f
        var offsetY = getY() + 80f
        var btnCount = 0

        for(mod in Abyss.getInstance().moduleRegistry.modules) {

            val comp = ModButtonComponent(mod, offsetX, offsetY)
            mods.add(comp)

            comp.settings = false

            offsetX += comp.getWidth() + 10f
            btnCount++

            if(btnCount == 4) {
                offsetX = getX() + getSideBarWidth() + 25f
                btnCount = 0
                offsetY += comp.getHeight() + 10f
            }
        }
    }

    override fun tick() {
        super.tick()

        if(currentPage == Page.MODULES) {

            mods.forEach {
                it.update()
            }
        }
    }

    override fun render(mouseX: Int, mouseY: Int, tickDelta: Float) {

        val skia = Abyss.getInstance().skia

        skia.render {

            skia.drawRoundedRect(getX(), getY(), getMenuWidth(), getMenuHeight(), 35f, skia.paint(Color(5, 5, 5)))

            skia.scissor(getX(), getY(), getMenuWidth(), getMenuHeight()) {

                skia.drawRoundedRect(getX() + 10f, getY() + 10f, getSideBarWidth(), getMenuHeight() - 20f, 30f, skia.paint(Color(14, 14, 14)))

                skia.drawRoundedRect(getX() + getSideBarWidth() + 18f, getY() + 10f, getMenuWidth() - getSideBarWidth() - 30f, getMenuHeight() - 20f, 30f, skia.paint(Color(14, 14, 14)))

                animatePage.setAnimation(pages.indexOf(getByPage(currentPage)) * (30f * Window(MinecraftClient.getInstance()).scaleFactor), 18.0);

                skia.drawRoundedGradientRect((getX() + 10f) + getSideBarWidth() / 2f - (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f) / 2f, getY() + 50f + animatePage.value, (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f), (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f), 25f, Color(0, 111, 238), Color(0, 86, 185))
                skia.drawRoundedOutline((getX() + 10f) + getSideBarWidth() / 2f - (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f) / 2f, getY() + 50f + animatePage.value, (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f), (30f * Window(MinecraftClient.getInstance()).scaleFactor - 10f), 25f, 1.5f, skia.paint(Color(0, 86, 185)))

                pages.forEach {
                    it.render(skia, it.x, it.y)
                }

                modMenuButtons.forEach {
                    it.render(skia, it.x, it.y)
                }

                when(currentPage) {

                    Page.MODULES -> {

                        if(currentPage == Page.MODULES) {
                            pageTitleAnimation.setAnimation(getX() + getSideBarWidth() + 40f, 12.0)
                        } else {
                            pageTitleAnimation.setAnimation(getX(), 12.0)
                        }

                        if(!modSettings) {
                            skia.drawText("Modules", Fonts.interMedium(40f), pageTitleAnimation.value, getY() + 35f, skia.paint(Color.WHITE))

                            skia.drawRect(getX() + getSideBarWidth() + 40f, (getY() + 35) + skia.getTextHeight("Modules", Fonts.interMedium(40f)) + 8f, (getMenuWidth() - getSideBarWidth()) - 60f, 1f, skia.paint(Color(25, 25, 25)))
                        }

                        mods.forEach {
                            it.render(skia, it.x, it.y)
                        }
                    }

                    Page.SETTINGS -> {
                        skia.drawText("Settings", Fonts.interMedium(40f), getX() + getSideBarWidth() + 40f, getY() + 35f, skia.paint(Color.WHITE))

                        settings.forEach {
                            it.render(skia, it.x, it.y)
                        }
                    }

                    Page.THEMES -> {
                        skia.drawText("Themes", Fonts.interMedium(40f), getX() + getSideBarWidth() + 40f, getY() + 35f, skia.paint(Color.WHITE))

                        skia.drawText("Coming Soon...", Fonts.interMedium(50f), getX() + getMenuWidth() / 2f - skia.getTextWidth("Coming Soon...", Fonts.interMedium(50f)) / 2f, getY() + getMenuHeight() / 2f - skia.getTextHeight("Coming Soon...", Fonts.interMedium(50f)) / 2f, skia.paint(Color.WHITE))
                    }
                }
            }
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {

        if(!InputUtils.isMouseOver(getX(), getY(), getMenuWidth(), getMenuHeight())) return

        pages.forEach {
            it.onClick(button)
        }

        modMenuButtons.forEach {
            it.onClick(button)
        }

        when(currentPage) {

            Page.MODULES -> {

                mods.forEach {
                    it.onClick(button)
                }
            }

            Page.SETTINGS -> {

                settings.forEach {
                    it.onClick(button)
                }
            }

            Page.THEMES -> {
                return
            }
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, button: Int) {

        if(!InputUtils.isMouseOver(getX(), getY(), getMenuWidth(), getMenuHeight())) return

        when(currentPage) {

            Page.MODULES -> {
                mods.forEach {
                    it.onMouseRelease()
                }
            }

            Page.SETTINGS -> {

                settings.forEach {
                    it.onMouseRelease()
                }
            }

            Page.THEMES -> {
                return
            }
        }
    }

    override fun keyPressed(id: Char, code: Int) {
        super.keyPressed(id, code)

        when(currentPage) {

            Page.MODULES -> {

                mods.forEach {
                    it.onKey(id, code)
                }
            }

            Page.SETTINGS -> {

                settings.forEach {
                    it.onKey(id, code)
                }
            }

            Page.THEMES -> {
                return
            }
        }
    }

    override fun resize(client: MinecraftClient?, width: Int, height: Int) {
        super.resize(client, width, height)

        var offsetY = getY() + 50f

        pages.forEach {
            it.update((getX() + 10f) + getSideBarWidth() / 2f - it.getSize() / 2f, offsetY)
            offsetY += it.getSize() + 10f
        }

        initMods()
        initPages()
    }

    fun getX(): Float {
        return Display.getWidth() / 2f - getMenuWidth() / 2f
    }

    fun getY(): Float {
        return Display.getHeight() / 2f - getMenuHeight() / 2f
    }

    fun getMenuWidth(): Float {
        return 460f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    fun getMenuHeight(): Float {
        return 300f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    fun getSideBarWidth(): Float {
        return 30f * Window(MinecraftClient.getInstance()).scaleFactor
    }

    fun getByPage(page: Page): PageButtonComponent {
        return pages.stream().filter { it.page == page }.findFirst().orElse(null)
    }
}