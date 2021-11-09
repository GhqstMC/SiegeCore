package net.siegerpg.siege.core.utils.cache;

import kotlin.Pair;
import net.siegerpg.siege.core.Core;
import net.siegerpg.siege.core.items.CustomItem;
import net.siegerpg.siege.core.items.CustomItemUtils;
import net.siegerpg.siege.core.items.enums.StatTypes;
import net.siegerpg.siege.core.items.types.misc.CustomMaterial;
import net.siegerpg.siege.core.listeners.ArmorEquip.ArmorEquipEvent;
import net.siegerpg.siege.core.skills.Skill;
import net.siegerpg.siege.core.utils.Levels;
import net.siegerpg.siege.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerData implements Listener {
    public static HashMap<Player, Boolean> hasActionBar = new HashMap<>();
    public static HashMap<Player, Boolean> broadcastTips = new HashMap<>();
    public static HashMap<Player, Double> playerHealth = new HashMap<>();
    public static HashMap<Player, Double> playerCurrentMana = new HashMap<>();
    public static HashMap<Player, Double> playerMana = new HashMap<>();
    public static HashMap<Player, Location> playerDeathLocations = new HashMap<>();

    public static HashMap<Player, HashMap<Integer, Skill>> playerSkills = new HashMap<>();
    public static HashMap<Player, ArrayList<Action>> playerTriggers = new HashMap<>();

    @EventHandler
    public void onEnable(PluginEnableEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setStats(player);
            hasActionBar.put(player, false);
            PlayerData.broadcastTips.put(player, true);
            //playerSkills.put(player, SkillUtils.decode(Skills.INSTANCE.getSkills(player)));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        hasActionBar.put(player, false);
        setStats(player);
        //playerSkills.put(player, SkillUtils.decode(Skills.INSTANCE.getSkills(player)));
        if (!PlayerData.broadcastTips.containsKey(player)) {
            PlayerData.broadcastTips.put(player, true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        playerHealth.remove(player);
        playerMana.remove(player);
        playerCurrentMana.remove(player);
        playerSkills.remove(player);
    }

    public static void setStats(Player player) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Core.plugin(), () -> {
            playerHealth.put(
                    player,
                    CustomItemUtils.INSTANCE.getPlayerStat(player, StatTypes.HEALTH) + player.getMaxHealth() + player.getLevel() * 2);

            playerMana.put(
                    player,
                    CustomItemUtils.INSTANCE.getPlayerStat(player, StatTypes.MANA));

        }, 2);
    }


    @EventHandler(priority =  EventPriority.LOWEST)
    public void onEquip(ArmorEquipEvent e) {
        CustomItem item = CustomItemUtils.INSTANCE.getCustomItem(e.getNewArmorPiece());
        if (item == null) {
            setStats(e.getPlayer());
            return;
        }
        if (item.getLevelRequirement() == null) return;
        Pair<Short, Integer> expLevel = Levels.INSTANCE.blockingGetExpLevel(e.getPlayer());
        if (item.getLevelRequirement() > (expLevel != null ? expLevel.getFirst() : 0)) {
            e.getPlayer().sendTitle("&c&lSORRY!", ChatColor.RED + "Too weak to use this armor's stats", 1, 80, 1);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f, 1.0f);
            e.setCancelled(true);
            return;
        }
        setStats(e.getPlayer());
    }

    @EventHandler
    public void toolSwitch(PlayerItemHeldEvent e) {
        CustomItem item = CustomItemUtils.INSTANCE.getCustomItem(e.getPlayer().getInventory().getItemInMainHand());
        if (item != null) {
            if (!(item instanceof CustomMaterial)) {
                setStats(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        playerDeathLocations.put(player, player.getLocation());
    }
}
