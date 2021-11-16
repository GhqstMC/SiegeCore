package net.siegerpg.siege.core.items.implemented.weapons.melee.light

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.weapons.CustomMeleeWeapon
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ToughRefinedDagger() : CustomMeleeWeapon(
	name = "Tough Refined Dagger",
	customModelData = 110010,
	description = listOf("A dagger, but shinier", "and more pointy"),
	levelRequirement = 37,
	material = Material.WOODEN_SWORD,
	baseStats = CustomItemUtils.statMap(strength = 34.0, toughness = 60.0),
	attackSpeed = 1.6
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