package net.siegerpg.siege.core.items.implemented.misc.statgems.toughGems

import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.enums.StatTypes
import net.siegerpg.siege.core.items.types.misc.StatGemType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SimpleToughGem() : StatGemType(
		name = "Simple Defense Gem",
		customModelData = 540004,
		description = listOf("A simple gem"),
		levelRequirement = 20,
		material = Material.POPPED_CHORUS_FRUIT,

		statType = StatTypes.DEFENSE,
		statAmount = 30.0,
		quality = 90
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