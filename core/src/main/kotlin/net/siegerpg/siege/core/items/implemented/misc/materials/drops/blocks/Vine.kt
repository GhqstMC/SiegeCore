package net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.misc.CustomMaterial
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Vine() : CustomMaterial(
		name = "Vine",
		customModelData = 320004,
		description = listOf("Woven around trees", "and abandoned lands"),
		levelRequirement = 0,
		material = Material.FLINT,
		upgradeStats = hashMapOf(
				2 to CustomItemUtils.statMap(regeneration = 0.1),
				3 to CustomItemUtils.statMap(regeneration = 1.0),
				4 to CustomItemUtils.statMap(regeneration = 4.0),
				5 to CustomItemUtils.statMap(regeneration = 16.0)
		                        )
		) {

	constructor(quality : Int) : this() {
		this.quality = quality
		this.rarity = Rarity.getFromInt(quality)
		this.serialize()
	}

	constructor(item : ItemStack) : this() {
		this.item = item
		deserialize()
	}

	companion object {

		fun tier(tier : Int) : Vine {
			val newItem = Vine(0)
			newItem.tier = tier
			return newItem
		}
	}

}