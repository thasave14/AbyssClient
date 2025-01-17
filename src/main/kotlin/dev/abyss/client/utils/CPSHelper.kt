package dev.abyss.client.utils

import org.lwjgl.input.Mouse

object CPSHelper {

    val leftClicks = mutableListOf<Long>()
    var wasLeftPressed: Boolean = false
    var lastLeftPressed: Long = 0L

    val rightClicks = mutableListOf<Long>()
    var wasRightPressed: Boolean = false
    var lastRightPressed: Long = 0L

    fun update() {

        val leftPressed = Mouse.isButtonDown(0)

        val rightPressed = Mouse.isButtonDown(1)

        if(leftPressed != wasLeftPressed) {
            lastLeftPressed = System.currentTimeMillis()
            wasLeftPressed = leftPressed

            if(leftPressed) {
                leftClicks.add(lastLeftPressed)
            }
        }

        if(rightPressed != wasRightPressed) {
            lastRightPressed = System.currentTimeMillis()
            wasRightPressed = rightPressed

            if(rightPressed) {
                rightClicks.add(lastRightPressed)
            }
        }
    }

    fun getLeftCPS(): Int {

        val time: Long = System.currentTimeMillis()

        leftClicks.removeIf {
            aLong -> aLong + 1000L < time
        }

        return leftClicks.size
    }

    fun getRightCPS(): Int {
        val time: Long = System.currentTimeMillis()

        rightClicks.removeIf {
                aLong -> aLong + 1000L < time
        }

        return rightClicks.size
    }
}