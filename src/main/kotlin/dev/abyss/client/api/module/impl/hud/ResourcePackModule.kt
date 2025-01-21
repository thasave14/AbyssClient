package dev.abyss.client.api.module.impl.hud

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.hud.HudModule
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.Slider
import dev.abyss.client.api.module.settings.Switch
import dev.abyss.client.event.Subscribe
import dev.abyss.client.event.impl.ChangePackEvent
import dev.abyss.client.skia.Skia
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.resource.ResourcePackLoader
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.client.util.Window
import java.awt.Color

class ResourcePackModule : HudModule() {

    /**
     * @author PolyFrost
     * Source: EvergreenHUD
     */
    var pack: ResourcePackLoader.Entry? = getResourcePack()

    /**
     * @author PolyFrost
     * Source: EvergreenHUD
     */
    val defaultIcon = mc.textureManager.registerDynamicTexture("texturepackicon", NativeImageBackedTexture(mc.resourcePackLoader.defaultResourcePack.icon))

    init {

        CategoryDivider(
            name = "Background ",
            module = this
        )

        Switch(
            name = "Background",
            module = this,
            toggled = true,
            category = "Background"
        )

        Slider(
            name = "Corner Radius",
            module = this,
            minSlider = 0f,
            currentSlider = 0f,
            maxSlider = 20f,
            category = "Background"
        )

        CategoryDivider(
            name = "Text ",
            module = this
        )

        Switch(
            name = "Text Shadow",
            module = this,
            toggled = true,
            category = "Text"
        )
    }

    override fun render(skia: Skia, x: Float, y: Float) {

        if(getSetting("Background").toggled) {

            skia.render {
                skia.drawRoundedRect(x, y, getWidth(), getHeight(), getSetting("Corner Radius").currentSlider, skia.paint(Color(0, 0, 0, 90)))
            }
        }

        /**
         * @author PolyFrost
         * Source: EvergreenHUD
         */
        pack?.bindIcon(mc.textureManager) ?: mc.textureManager.bindTexture(defaultIcon)
        DrawableHelper.drawTexture(getDescaledX().toInt(), getDescaledY().toInt(), 0f, 0f, 64, 64, getDescaledH().toInt(), getDescaledH().toInt(), 64f, 64f)

        mc.textRenderer.draw(getResourcePackName(), (getDescaledX() + getDescaledH()) + 2f, getDescaledY() + getDescaledH() / 2f - mc.textRenderer.fontHeight / 2f, -1, getSetting("Text Shadow").toggled)
    }

    override fun getWidth(): Float {
        val window = Window(mc)
        return (mc.textRenderer.getStringWidth(getResourcePackName()) * window.scaleFactor).toFloat() + (35f * window.scaleFactor)
    }

    override fun getHeight(): Float {
        return 28f * Window(mc).scaleFactor
    }

    override fun getMetaData(): MetaData {
        return MetaData("Resourcepack Display", "Display your Current Resourcepack", Type.HUD)
    }

    /**
     * @author PolyFrost
     * Source: EvergreenHUD
     */
    private fun getResourcePack(): ResourcePackLoader.Entry? {
        return mc.resourcePackLoader.availableResourcePacks.getOrNull(mc.resourcePackLoader.availableResourcePacks.size - 1)
    }

    /**
     * @author PolyFrost
     * Source: EvergreenHUD
     */
    private fun getResourcePackName(): String {
        val resourcePackName = pack?.name ?: "Default";
        return resourcePackName;
    }

    /**
     * @author PolyFrost
     * Source: EvergreenHUD
     */
    @Subscribe
    fun onChangePack(event: ChangePackEvent) {
        pack = getResourcePack()
    }
}