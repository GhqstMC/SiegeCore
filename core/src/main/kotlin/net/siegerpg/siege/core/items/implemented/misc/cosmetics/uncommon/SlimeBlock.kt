package net.siegerpg.siege.core.items.implemented.misc.cosmetics.uncommon

import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.misc.Cosmetic
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SlimeBlock() : Cosmetic(
		name = "Slime Block",
		customModelData = 1,
		description = listOf(""),
		material = Material.SLIME_BLOCK,
		quality = 50
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

}