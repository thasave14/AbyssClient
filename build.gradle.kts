import org.gradle.internal.os.OperatingSystem
import net.fabricmc.loom.task.RemapJarTask

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"
    java
    id("maven-publish")

    alias(libs.plugins.loom)
    alias(libs.plugins.loom.legacy)
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = property("maven_group")!!
version = property("mod_version")!!

val minecraft_version by properties
val yarn_build by properties
val loader_version by properties

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    jcenter()
}

loom {

}

legacyLooming {

}

val lwjglVersion = "3.3.1"

var lwjglNatives = ""

lwjglNatives = when(OperatingSystem.current()) {
    OperatingSystem.WINDOWS -> "natives-windows"
    OperatingSystem.LINUX -> "natives-linux"
    else -> throw IllegalArgumentException("Could Not Find Natives For Operating System: ${OperatingSystem.current().name}")
}

val shadowImpl: Configuration = configurations.create("shadowImpl") {
    isCanBeResolved = true
}

tasks.shadowJar {

    dependencies {
        include(dependency("io.github.humbleui:skija-windows-x64:0.116.2"))
        include(dependency("io.github.humbleui:skija-linux-x64:0.116.2"))
        include(dependency("io.github.humbleui:types:0.1.1"))
        include(dependency("io.github.humbleui:skija-shared:0.116.2"))
        include(dependency("com.sun.jna:jna:3.0.9"))
    }
}

dependencies {

    "minecraft" ("com.mojang:minecraft:${minecraft_version}")
    "mappings" ("net.legacyfabric:yarn:${minecraft_version}+build.$yarn_build:v2")
    "modImplementation" ("net.fabricmc:fabric-loader:${loader_version}")

    implementation("io.github.humbleui:skija-windows-x64:0.116.2")
    implementation("io.github.humbleui:skija-linux-x64:0.116.2")

    implementation("com.sun.jna:jna:3.0.9")

    implementation("co.gongzh.procbridge:procbridge:1.1.1")

    implementation ("com.google.code.gson:gson:2.8.9")
}

tasks.register("remapShadowJar", RemapJarTask::class.java) {
    dependsOn(ShadowJar::class)
}

tasks.build.get().dependsOn("remapShadowJar")

tasks.processResources {
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to version))
    }
}

tasks.jar {

    from("LICENSE") {
        rename {
            "${it}_${archiveBaseName.get()}"
        }
    }
}

kotlin {
    jvmToolchain(17)
}