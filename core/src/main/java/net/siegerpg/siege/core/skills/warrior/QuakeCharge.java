package net.siegerpg.siege.core.skills.warrior;

import net.siegerpg.siege.core.*;
import net.siegerpg.siege.core.items.*;
import net.siegerpg.siege.core.items.enums.*;
import net.siegerpg.siege.core.miscellaneous.Utils;
import net.siegerpg.siege.core.skills.Skill;
import net.siegerpg.siege.core.skills.SkillClass;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.*;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

public class QuakeCharge extends Skill {

	final int initCooldown = 30 * 1000;
	final int initManaCost = 150;
	final int initGoldCost = 5000;
	final int duration = 5;
	final double damageMulti = 1.5;

	public QuakeCharge() {
		this.identifier = "2_A_3";
		this.skillClass = SkillClass.WARRIOR;
		this.name = "Quake Charge";
		this.description = List.of("Charge in any direction for",
		                           "5 seconds. Inflicts +50% damage",
		                           "to any mobs in the way.",
		                           "Weakens you for 5 seconds");
	}

	@Override
	public List< String > getDescription() {
		return this.description;
	}
	@Override
	public String getName(int level) {

		return this.name + " Lvl. " + level;
	}

	@Override
	public List< String > getDescription(int level) {

		return List.of("Charge in any direction for",
		               "5 seconds. Inflicts +"+((getDamageMulti(level) - 1) * 100)+"% damage",
		               "to any mobs in the way.",
		               "Weakens you for "+getDuration(level)+" seconds");

	}


	@Override
	public Duration getCooldown(int level) {
		return Duration.ofMillis(initCooldown);
	}

	@Override
	public int getManaCost(int level) {
		return (int) (this.initManaCost + Math.ceil(this.initManaCost * (level-1) * 0.02));
	}

	@Override
	public int getGoldCost(int level) {
		return (int) (this.initGoldCost * level * 2.5);
	}

	public double getDuration(int level) {
		return (this.duration) + (level-1);
	}
	public double getDamageMulti(int level) {
		return Utils.round(((this.damageMulti) + ((level-1) * 0.025)), 2);
	}

	@Override
	public boolean trigger(@NotNull Player player, int level) {
		// First we check if the cooldown and mana are respected (we run the code common to all skills)
		// If the trigger() method returns false it means that the execution was not successful (for example the cooldown wasn't finished) so we stop executing and return false
		if (!super.trigger(player, level)) return false;

		new BukkitRunnable() {

			int counter = 0;

			@Override
			public void run() {

				if(counter > 20 * getDuration(level)){

					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1));

					triggerEnd(player, level);
					
					this.cancel();
				}

				for(Entity entity: player.getNearbyEntities(1.5, 1.5, 1.5)){
					if(entity instanceof Player || !(entity instanceof LivingEntity e)) continue;

					e.damage(getDamageMulti(level) * CustomItemUtils.INSTANCE.getPlayerStat(player, StatTypes.STRENGTH));
				}

				player.setVelocity(player.getLocation().getDirection().setY(0).normalize().multiply(0.1));

				counter++;

			}
		}.runTaskTimer(Core.plugin(), 0L, 1L);
		
		return true;
	}

	@Override
	public void triggerEnd(@NotNull Player player, int level) {

		super.triggerEnd(player, level);

	}

}
