package net.siegerpg.siege.core.levelReward

import net.siegerpg.siege.core.items.enums.StatTypes
import net.siegerpg.siege.core.items.implemented.misc.food.Sugar
import net.siegerpg.siege.core.items.implemented.misc.keys.crate.MobKey
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.Crystal
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.blocks.RefinedMetal
import net.siegerpg.siege.core.items.implemented.misc.materials.drops.mobs.CorruptCrystal
import net.siegerpg.siege.core.items.implemented.misc.statgems.strengthGems.SimpleStrengthGem
import net.siegerpg.siege.core.items.implemented.weapons.ranged.Crossbow
import net.siegerpg.siege.core.listeners.GoldExpListener
import net.siegerpg.siege.core.miscellaneous.Utils
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Reward40(
		override val level : Short = 41,
		override val gold : Int = 0,
		override val items : List<ItemStack> = listOf(
				MobKey().getUpdatedItem(false).asQuantity(12)
		                                             ),
		override val stats : HashMap<StatTypes, Int> = hashMapOf(),
		override val skillPoints : Int = 0
              ) : LevelReward