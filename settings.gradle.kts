pluginManagement {

    repositories {

        maven("https://maven.fabricmc.net/")
        maven("https://maven.legacyfabric.net/")

        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
    }
}

rootProject.name = "Abyss"