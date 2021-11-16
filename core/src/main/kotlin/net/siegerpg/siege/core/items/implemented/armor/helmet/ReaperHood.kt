package net.siegerpg.siege.core.items.implemented.armor.helmet

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.armor.CustomHelmet
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ReaperHood() : CustomHelmet(
	name = "Reaper Hood",
	customModelData = 1,
	description = listOf("Allow yourself to be", "shrouded in darkness"),
	levelRequirement = 39,
	material = Material.LEATHER_HELMET,
	baseStats = CustomItemUtils.statMap(
		health = 40.0,
		strength = 10.0,
		regeneration = 10.0,
		luck = -15.0,
		toughness = -60.0
	),
	leatherColor = Color.BLACK
) {

	constructor(quality: Int) : this() {
		this.quality = quality
		this.rarity = Rarity.getFromInt(quality)
		this.serialize()
	}

	constructor(item: ItemStack) : this() {
		this.item = item
		deserialize()
	}

}