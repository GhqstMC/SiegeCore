package net.siegerpg.siege.core.items.implemented.misc.wands.flamingHotTorches

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.weapons.CustomWand
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class HealthyFlamingHotTorch() : CustomWand(
    name = "Healthy Flaming Hot Torch",
    customModelData = 140007,
    description = listOf("This weapon was said to have the ability", "to generate mass amounts of electricity"),
    levelRequirement = 24,
    material = Material.WOODEN_HOE,
    baseStats = CustomItemUtils.statMap(strength = 15.0, luck = 14.0, health = 12.0),
    
    range = 19,
    red = 255,
    green = 0,
    blue = 0,
    damageRadius = 2.0
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