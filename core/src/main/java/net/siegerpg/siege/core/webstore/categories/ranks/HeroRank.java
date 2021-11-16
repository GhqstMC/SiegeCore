package net.siegerpg.siege.core.webstore.categories.ranks;

import net.siegerpg.siege.core.miscellaneous.Utils;
import net.siegerpg.siege.core.miscellaneous.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HeroRank extends WebstoreRanks {

	public HeroRank () {

		super("rank", "hero");
	}

	@SuppressWarnings("Deprecated")
	@Override
	public void completePurchase (UUID uuid) {

		Player player = Bukkit.getPlayer(uuid);
		if (player == null) return;

		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		String cmd = "lp user " + player.getName() + " parent add Hero";
		Bukkit.dispatchCommand(console, cmd);

		int highestPV = Utils.getHighestPV(player);
		int diff = 54 - highestPV;
		int addPV = highestPV + 5;

		//this is so inefficient it hurts to look at
		if (VaultHook.perms.playerInGroup(player, "warrior")) {
			addPV = highestPV + 3;
			if (diff < 3) addPV = diff;
			ConsoleCommandSender console4 = Bukkit.getServer().getConsoleSender();
			String cmd3 = "lp user " + player.getName() + " parent remove warrior";
			Bukkit.dispatchCommand(console4, cmd3);
		} else if (VaultHook.perms.playerInGroup(player, "gladiator")) {
			addPV = highestPV + 2;
			if (diff < 2) addPV = diff;
			ConsoleCommandSender console4 = Bukkit.getServer().getConsoleSender();
			String cmd3 = "lp user " + player.getName() + " parent remove gladiator";
			Bukkit.dispatchCommand(console4, cmd3);
		} else {
			if (diff < 5) addPV = diff;
		}

		ConsoleCommandSender console2 = Bukkit.getServer().getConsoleSender();
		String cmd2 = "lp user " + player.getName() + " permission set cosmicvaults.amount." + addPV + " true global";
		Bukkit.dispatchCommand(console2, cmd2);

		Bukkit.broadcastMessage(Utils.tacc(""));
		Bukkit.broadcastMessage(Utils.tacc("  &b" + player.getName() + " has bought &bHero &erank!"));
		Bukkit.broadcastMessage(Utils.tacc("  &bhttps://store.siegerpg.net/"));
		Bukkit.broadcastMessage(Utils.tacc(""));
	}

}
