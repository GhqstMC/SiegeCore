package net.siegerpg.siege.core.items.implemented.armor.leggings

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.Bone
import net.siegerpg.siege.core.items.recipes.recipes
import net.siegerpg.siege.core.items.types.armor.CustomLeggings
import net.siegerpg.siege.core.utils.Utils
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class TitaniumLeggings() : CustomLeggings(
    name = "Titanium Leggings",
    customModelData = 1,
    description = listOf("Made of hard titanium"),
    levelRequirement = 50,
    material = Material.IRON_LEGGINGS,
    baseStats = CustomItemUtils.statMap(health = 60.0, toughness = 200.0, regeneration = 10.0, luck = -25.0, strength = -40.0),
) {

    constructor(quality: Int): this() {
        this.quality = quality
        this.rarity = Rarity.getFromInt(quality)
        this.serialize()
    }

    constructor(item: ItemStack): this() {
        this.item = item
        deserialize()
    }

}