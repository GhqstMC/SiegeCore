package net.siegerpg.siege.core.items.implemented.armor.boots.slimyBoots

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.armor.CustomBoots
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class LuckySlimyBoots() : CustomBoots(
		name = "Lucky Slimy Boots",
		customModelData = 1,
		description = listOf(
				"Contrary to popular belief,",
				"slime boots don't make you jump high"
		                    ),
		levelRequirement = 7,
		material = Material.LEATHER_BOOTS,
		baseStats = CustomItemUtils.statMap(luck = 5.0),
		leatherColor = Color.LIME,
		gearSetInfo = listOf("Jump high like a slime!")
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