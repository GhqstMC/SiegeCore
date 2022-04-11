package net.siegerpg.siege.core.items.implemented.armor.chestplate

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.armor.CustomChestplate
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class DavyTunic() : CustomChestplate(
		name = "Davy's Tunic",
		customModelData = 1,
		description = listOf("Davy Jones is the fifth guardian"),
		levelRequirement = 33,
		material = Material.LEATHER_CHESTPLATE,
		baseStats = CustomItemUtils.statMap(health = 150.0, strength = 40.0, defense = -40.0),
		gearSetInfo = listOf("Dolphin's Grace II, Speed I, Water breathing II"),
		leatherColor = Color.fromRGB(74, 104, 150)
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