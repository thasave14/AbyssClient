package dev.abyss.client.api.module.impl

import dev.abyss.client.api.module.MetaData
import dev.abyss.client.api.module.Module
import dev.abyss.client.api.module.Type
import dev.abyss.client.api.module.settings.CategoryDivider
import dev.abyss.client.api.module.settings.Switch

class OldAnimationsModule : Module() {

    init {

        CategoryDivider(name = "Item Animations", module = this)

        Switch(
            name = "Block Hitting",
            module = this,
            toggled = true,
            category = "Item Animations"
        )

        Switch(
            name = "Eating/Drinking",
            module = this,
            toggled = true,
            category = "Item Animations"
        )

        Switch(
            name = "Bow Animations",
            module = this,
            toggled = true,
            category = "Item Animations"
        )

        Switch(
            name = "Rod Animations",
            module = this,
            toggled = true,
            category = "Item Animations"
        )

        CategoryDivider(name = "Player Animations", module = this)

        Switch(
            name = "1.7 Sneaking",
            module = this,
            toggled = true,
            category = "Player Animations"
        )

        Switch(
            name = "Third Person Held Items",
            module = this,
            toggled = true,
            category = "Player Animations"
        )
    }

    override fun getMetaData(): MetaData {
        return MetaData("1.7 Visuals", "Reverts The Visuals Back To 1.7", Type.VISUAL)
    }
}