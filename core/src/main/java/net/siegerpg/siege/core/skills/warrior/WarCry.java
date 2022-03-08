package net.siegerpg.siege.core.skills.warrior;

import net.siegerpg.siege.core.miscellaneous.cache.*;
import net.siegerpg.siege.core.skills.Skill;
import net.siegerpg.siege.core.skills.SkillClass;
import org.bukkit.entity.Player;
import org.bukkit.potion.*;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

public class WarCry extends Skill {

	final int initCooldown = 45 * 1000;
	final int initManaCost = 100;
	final int initGoldCost = 5000;
	final int duration = 10;
	final double manaRestoreAmt = 0.75;

	public WarCry() {
		this.identifier = "2_B_1";
		this.skillClass = SkillClass.WARRIOR;
		this.name = "War Cry";
		this.description = List.of("Increase speed by 20%,",
		                           "or 40% if weakened, and",
		                           "restore 25% of your mana.",
		                           "Lasts 10 seconds");
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

		return List.of("Increase speed by 20%,",
		               "or 40% if weakened, and",
		               "restore "+(1-(getManaRestoreAmt(level)) * 100)+"% of your mana.",
		               "Lasts "+getDuration(level)+" seconds");
	}

	@Override
	public Duration getCooldown(int level) {
		return Duration.ofMillis(initCooldown);
	}

	@Override
	public int getManaCost(int level) {
		return (int) (this.initManaCost + Math.ceil(this.initManaCost * (level-1) * 0.03));
	}

	@Override
	public int getGoldCost(int level) {
		return (int) (this.initGoldCost * level * 2.5);
	}

	public double getDuration(int level) {
		return (this.duration) + (level-1);
	}
	public double getManaRestoreAmt(int level) {
		return (this.manaRestoreAmt) - ((level-1)*0.015);
	}

	@Override
	public boolean trigger(@NotNull Player player, int level) {
		// First we check if the cooldown and mana are respected (we run the code common to all skills)
		// If the trigger() method returns false it means that the execution was not successful (for example the cooldown wasn't finished) so we stop executing and return false
		if (!super.trigger(player, level)) return false;


        if(player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
	        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (20 * getDuration(level)), 2));
        } else{
	        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (20 * getDuration(level)), 1));
        }

		Integer playerMaxMana = PlayerData.playerMana.get(player);
		Integer playerMana = PlayerData.playerCurrentMana.get(player);

		if (playerMana != null && playerMaxMana != null) {

			PlayerData.playerCurrentMana.put(player, Math.min(playerMaxMana, (int) (playerMana * (2 - getManaRestoreAmt(level)))));

		}
		
		return true;
	}

	@Override
	public void triggerEnd(@NotNull Player player, int level) {

		super.triggerEnd(player, level);

	}

}
