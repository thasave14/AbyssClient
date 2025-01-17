package dev.abyss.client.api.module.hud

/**
 * Divides the screen into nine different sections. Each of them has its own coordinate system with the origin being
 * located at the position corresponding to the name:
 * - TOP_LEFT -> origin in the top left
 * - BOTTOM_CENTER -> origin the bottom center
 *
 * The x and y value from the HUD widget range from 0.0 to 1.0 for the corner sections and from -0.5 to 0.5 in the center sections.
 *
 * @author hobbyshop
 */
enum class ScreenSection {

    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT

}