package net.siegerpg.siege.core.items.implemented.weapons.melee.light.daggers

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ToughDagger() : CustomMeleeWeapon(
	name = "Tough Dagger",
	customModelData = 110006,
	description = listOf("The prison shank's big brother"),
	levelRequirement = 20,
	material = Material.WOODEN_SWORD,
	baseStats = CustomItemUtils.statMap(strength = 20.0, toughness = 40.0),

	attackSpeed = 1.7
) {

	constructor(quality: Int) : this() {
		this.quality = quality
		this.rarity = Rarity.getFromInt(quality)
		this.serialize()
	}

	constructor(item: ItemStack) : this() {
		this.item = item
		this.deserialize()
	}

}