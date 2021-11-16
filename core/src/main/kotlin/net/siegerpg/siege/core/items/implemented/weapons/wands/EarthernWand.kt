package net.siegerpg.siege.core.items.implemented.weapons.wands

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.weapons.CustomWand
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class EarthernWand() : CustomWand(
	name = "Earthern Wand",
	customModelData = 140008,
	description = listOf("Life forces from earthern materials", "glow around this object"),
	levelRequirement = 28,
	material = Material.WOODEN_HOE,
	baseStats = CustomItemUtils.statMap(strength = 28.0, luck = 10.0),
	range = 17,
	red = 0,
	green = 204,
	blue = 0,
	damageRadius = 2.0
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