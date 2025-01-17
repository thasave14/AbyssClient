package dev.abyss.client.api.cosmetic

import dev.abyss.client.api.cosmetic.cloak.Cloak
import dev.abyss.client.api.cosmetic.wings.Wings
import net.minecraft.util.Identifier

object CosmeticManager {

    @JvmStatic
    val cosmetics: MutableList<Cosmetic> = mutableListOf()

    @JvmStatic
    var currentCloak: Cloak? = null

    @JvmStatic
    var currentWings: Wings? = null

    @JvmStatic
    fun init() {

        cosmetics.add(Cloak("Abyss White", Identifier("abyss/cosmetic/cloak/Abyss.png")))
        cosmetics.add(Cloak("Forest Dusk", Identifier("abyss/cosmetic/cloak/Forest Dusk.png")))
        currentCloak = getCloak("Abyss White")
    }

    @JvmStatic
    fun getCloak(name: String): Cloak {
        return cosmetics.stream().filter { it.name == name && (it is Cloak) }.findFirst().orElse(null) as Cloak
    }
}