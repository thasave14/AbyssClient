package dev.abyss.client.skia

import net.minecraft.client.util.Window

/**
 * @author iLofiz
 * Skia Value Helpers
 */

fun scale(value: Float, window: Window): Float = value * window.scaleFactor