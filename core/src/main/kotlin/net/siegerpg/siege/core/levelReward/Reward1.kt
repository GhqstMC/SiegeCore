package net.siegerpg.siege.core.levelReward

import net.siegerpg.siege.core.items.enums.StatTypes
import net.siegerpg.siege.core.items.implemented.weapons.ranged.ScrapyardBow
import net.siegerpg.siege.core.miscellaneous.Utils
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Reward1(
		override val level : Short = 2,
		override val gold : Int = 200,
		override val items : List<ItemStack> = listOf(),
		override val stats : HashMap<StatTypes, Int>,
		override val skillPoints : Int = 0
             ) : LevelReward