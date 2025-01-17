package dev.abyss.client.skia.font

import dev.abyss.client.skija.font.FontManager
import io.github.humbleui.skija.Font

object Fonts {

    fun poppinsRegular(size: Float): Font {
        return FontManager.font("Poppins-Regular.ttf", size)
    }

    fun poppinsMedium(size: Float): Font {
        return FontManager.font("Poppins-Medium.ttf", size)
    }

    fun poppinsSemiBold(size: Float): Font {
        return FontManager.font("Poppins-SemiBold.ttf", size)
    }

    fun poppinsBold(size: Float): Font {
        return FontManager.font("Poppins-Bold.ttf", size)
    }

    fun jetbrainsRegular(size: Float): Font {
        return FontManager.font("JetBrainsMono-Regular.ttf", size)
    }

    fun interRegular(size: Float): Font {
        return FontManager.font("Inter-Regular.ttf", size)
    }

    fun interMedium(size: Float): Font {
        return FontManager.font("Inter-Medium.ttf", size)
    }

    fun interSemiBold(size: Float): Font {
        return FontManager.font("Inter-SemiBold.ttf", size)
    }

    fun interBold(size: Float): Font {
        return FontManager.font("Inter-Bold.ttf", size)
    }

    fun icons(size: Float): Font {
        return FontManager.font("Icons.ttf", size)
    }
}