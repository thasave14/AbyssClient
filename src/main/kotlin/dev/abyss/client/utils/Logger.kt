package dev.abyss.client.utils

object Logger {

    @JvmStatic
    fun info(msg: String) {
        println("[Abyss/INFO]: $msg")
    }

    @JvmStatic
    fun warn(msg: String) {
        println("[Abyss/WARN]: $msg")
    }

    @JvmStatic
    fun error(msg: String) {
        println("[Abyss/ERROR]: $msg")
    }
}