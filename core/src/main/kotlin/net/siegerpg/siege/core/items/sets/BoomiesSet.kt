package net.siegerpg.siege.core.items.sets

import com.destroystokyo.paper.ParticleBuilder
import net.siegerpg.siege.core.Core
import net.siegerpg.siege.core.items.CustomItemUtils
import net.siegerpg.siege.core.items.enums.StatTypes
import net.siegerpg.siege.core.items.implemented.armor.boots.BoomiesHooves
import net.siegerpg.siege.core.items.implemented.armor.chestplate.BoomiesChestplate
import net.siegerpg.siege.core.items.implemented.armor.helmet.BoomiesHorns
import net.siegerpg.siege.core.items.implemented.armor.leggings.BoomiesLeggings
import net.siegerpg.siege.core.miscellaneous.Utils
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

class BoomiesSet : GearSet(
		helmets = hashSetOf(BoomiesHorns()),
		chestplates = hashSetOf(BoomiesChestplate()),
		leggings = hashSetOf(BoomiesLeggings()),
		boots = hashSetOf(BoomiesHooves())
                          ) {

	companion object {

		val cooldown : ArrayList<Player> = arrayListOf()
	}

	@EventHandler
	fun onSneak(e : PlayerToggleSneakEvent) {

		val player : Player = e.player
		var stop = true

		if (cooldown.contains(player)) return

		/*
		Check if player is holding down sneak for more than 2 seconds
		 */
		object : BukkitRunnable() {
			var counter : Int = 0
			override fun run() {
				counter++

				//player stops sneaking
				if (!player.isSneaking) {
					this.cancel()
					return
				}

				//player has held sneak for more than 2 seconds
				if (counter > 2) {
					this.cancel()
					stop = false
					return
				}

				player.spawnParticle(
						ParticleBuilder(Particle.CLOUD)
								.count(2)
								.offset(0.0, 0.0, 0.0)
								.particle(),
						player.location, 2
				                    )
				player.playSound(player.location, Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f)
			}
		}.runTaskTimer(Core.plugin(), 0, 20)

		if (stop) return
		val list : List<GearSet> = currentSets[player] ?: listOf()
		if (!Utils.contains(this, list)) return

		//cooldown for 10 seconds
		cooldown.add(player)
		object : BukkitRunnable() {
			override fun run() {
				cooldown.remove(player)
			}
		}.runTaskLater(Core.plugin(), 200)

		//pushes the player in the direction of the vector
		player.velocity = player.location.direction.normalize().multiply(3)

		object : BukkitRunnable() {
			override fun run() {

				//player stops suddenly
				player.velocity = Vector(0, 0, 0)

				player.spawnParticle(
						ParticleBuilder(Particle.CLOUD)
								.count(10)
								.offset(0.0, 0.0, 0.0)
								.particle(),
						player.location, 2
				                    )

				//damages all living entities within 2 blocks
				val dmg = CustomItemUtils.getPlayerStat(player, StatTypes.STRENGTH, player.inventory.itemInMainHand)
				for (entity in player.location.getNearbyLivingEntities(2.0)) {
					if (entity.equals(player)) continue
					player.damage(dmg, player)
				}
			}
		}.runTaskLater(Core.plugin(), 20)

	}
}