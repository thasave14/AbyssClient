package dev.abyss.client.api.cosmetic.cloak

import dev.abyss.client.api.cosmetic.Cosmetic
import dev.abyss.client.api.cosmetic.CosmeticType
import net.minecraft.util.Identifier

class Cloak(name: String, val location: Identifier) : Cosmetic(name, CosmeticType.CLOAK)