package net.siegerpg.siege.core.items.implemented.armor.chestplate

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.*
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.*
import net.siegerpg.siege.core.items.recipes.recipes
import net.siegerpg.siege.core.items.types.armor.CustomChestplate
import net.siegerpg.siege.core.items.types.armor.CustomHelmet
import net.siegerpg.siege.core.utils.Utils
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class LeatherChestplate() : CustomChestplate(
    name = "Hardened Leather Chestplate",
    customModelData = 1,
    description = listOf("Skin tight. Literally."),
    levelRequirement = 18,
    material = Material.LEATHER_CHESTPLATE,
    baseStats = CustomItemUtils.statMap(health = 25.0, toughness = 50.0),
    recipeList = recipes {
        recipe {
            shaped = true
            s1(Leather.tier(2))
            s3(Leather.tier(2))
            s4(Leather.tier(2))
            s5(Leather.tier(2))
            s6(Leather.tier(2))
            s7(Leather.tier(2))
            s8(Leather.tier(2))
            s9(Leather.tier(2))
            item { player, b ->
                val newItem = LeatherChestplate(if (b) 50 else Utils.randRarity())
                newItem.updateMeta(b)
                newItem
            }
        }
    }
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