package dev.abyss.client.skia

import io.github.humbleui.skija.Bitmap
import io.github.humbleui.skija.Image
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL


object Images {

    @JvmStatic
    fun getImage(path: String): Image {

        val bitmap = Bitmap()

        return Image.makeFromBitmap(bitmap)
    }
}