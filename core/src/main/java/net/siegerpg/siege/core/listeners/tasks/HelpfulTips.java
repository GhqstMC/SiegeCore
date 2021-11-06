package net.siegerpg.siege.core.listeners.tasks;

import net.siegerpg.siege.core.Core;
import net.siegerpg.siege.core.utils.Levels;
import net.siegerpg.siege.core.utils.Utils;
import net.siegerpg.siege.core.utils.cache.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class HelpfulTips implements Listener {

    /**
     * TIPS ARE SUBJECT TO CHANGE WITH NEW UPDATES
     */

    public static ArrayList<String> tips = new ArrayList<>() {
        {
            //Food
            add(Utils.tacc("&eFood can be farmed from all kinds of mobs!"));
            add(Utils.tacc("&eFood can be collected from farming carrots, pumpkins, and melons."));
            add(Utils.tacc("&eSome foods give special effects which you can use situationally."));

            //Stats
            add(Utils.tacc("&eArmor stats do not apply if you are not the appropriate level."));
            add(Utils.tacc("&eYou can still wear armor if you are not the appropriate level."));
            add(Utils.tacc("&eWeapon stats will not apply if you are not the appropriate level."));
            add(Utils.tacc("&eWeapons will only deal 1 damage if you are not the appropriate level."));

            //Leveling

            //Gear Collecting
            add(Utils.tacc("&eClick on weapon/armor vendors to craft stronger weapons and armor pieces."));
            add(Utils.tacc("&eClick on material vendors to upgrade your materials."));

            //Material Collecting

            //Dungeons

            //Cosmetic Keys

            //Boosters

            //Ranks
            add(Utils.tacc("&eWarrior rank gives you 9 more slots per player vault."));
            add(Utils.tacc("&eGladiator rank gives you 18 more slots per player vault."));
            add(Utils.tacc("&eHero rank gives you 27 more slots per player vault."));

            //Inventory Storage
            add(Utils.tacc("&eYou will gain a player vault for every 10 levels you rank up."));

            //Different types of NPCs
            add(Utils.tacc("&eYou can put your money in the bank at the village on the opposite corner of the map."));
            add(Utils.tacc("&eYou can buy food from the butcher at the village on the opposite corner of the map."));
            add(Utils.tacc("&eYou can gamble for money with Bart at the village on the opposite corner of the map."));
            add(Utils.tacc("&eHerbert the Scrapper will buy your items (but he's cheap about it)."));

            //Different types of Mobs
            add(Utils.tacc("&eGoblins and Wild Foxes can steal your money and run away with it."));
            add(Utils.tacc("&eZombified Diggers can duplicate when they hit you."));

            //Special Items (ex. stat gems)
            add(Utils.tacc("&eClick and drag stat gems onto a weapon or armor piece to apply it."));

            //Commands
            add(Utils.tacc("&eVisit our webstore using the /webstore command!"));
            add(Utils.tacc("&eJoin our discord using the /discord command!"));
            add(Utils.tacc("&eUse the /hub command to teleport to the hub."));
            add(Utils.tacc("&eUse the /spawn command to teleport to spawn."));

            //MISC Interactions
            add(Utils.tacc("&eMost plants can be broken for a chance to get gold and experience."));
            add(Utils.tacc("&eLooking to partner with us? DM Wrys#8935."));
            add(Utils.tacc("&eA sweeping effect is played every time a player damages a mob."));

        }
    };

    public void broadcastTasks() {
        tipsTask();
        webstoreDiscordTask();
    }

    public void tipsTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), () -> {
            int randNum = (int) (Math.random() * tips.size());
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!PlayerData.broadcastTips.get(p)) continue;
                p.sendMessage(Utils.tacc("\n&6&lTIP &r" + tips.get(randNum) + "&r\n&7To disable tips type /tips disable.\n\n "));
            }
        }, 6000, 6000);
    }

    public void webstoreDiscordTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.plugin(), () -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;
            Levels.INSTANCE.getExpLevel(new ArrayList<Player>(Bukkit.getOnlinePlayers()),
                    uuidPairHashMap -> {
                        uuidPairHashMap.forEach((uuid, shortIntegerPair) ->
                        {
                            if (shortIntegerPair.getFirst() > 10) return;
                            Player p = Bukkit.getPlayer(uuid);
                            if (p == null) return;
                            p.sendMessage(Utils.parse(""));
                            p.sendMessage(Utils.parse("  <aqua><bold>Join our <light_purple>discord<aqua> here!<reset>"));
                            p.sendMessage(Utils.tacc("  https://discord.siegerpg.net"));
                            p.sendMessage(Utils.parse(""));
                            p.sendMessage(Utils.parse("  <aqua><bold>Visit our <green>webstore<aqua> here!<reset>"));
                            p.sendMessage(Utils.tacc("  https://store.siegerpg.net/"));
                            p.sendMessage(Utils.parse(""));
                        });
                        return null;
                    });

        }, 12000, 6000);
    }
}
