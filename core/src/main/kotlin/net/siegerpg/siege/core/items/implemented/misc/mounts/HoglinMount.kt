package net.siegerpg.siege.core.items.implemented.misc.mounts

import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.Rarity
import net.siegerpg.siege.core.items.types.misc.CustomMount
import net.siegerpg.siege.core.items.types.misc.CustomTool
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class HoglinMount() : CustomMount(
		name = "Hoglin Mount",
		customModelData = 330016,
		description = listOf("Giant Pig"),
		material = Material.HOGLIN_SPAWN_EGG,
                                 ) {

	constructor(quality : Int) : this() {
		this.quality = 70
		this.rarity = Rarity.RARE
		this.serialize()
	}

	constructor(item : ItemStack) : this() {
		this.item = item
		deserialize()
	}

}