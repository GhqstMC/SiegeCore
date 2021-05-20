package net.siegerpg.siege.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.siegerpg.siege.core.commands.*;
import net.siegerpg.siege.core.drops.MobDrops;
import net.siegerpg.siege.core.items.recipes.CustomRecipe;
import net.siegerpg.siege.core.listeners.*;
import net.siegerpg.siege.core.listeners.ArmorEquip.ArmorListener;
import net.siegerpg.siege.core.listeners.NPC.*;
import net.siegerpg.siege.core.party.PartyConfig;
import net.siegerpg.siege.core.party.PartyManager;
import net.siegerpg.siege.core.portals.PortalConfig;
import net.siegerpg.siege.core.utils.Bank;
import net.siegerpg.siege.core.utils.PlayerBanking;
import net.siegerpg.siege.core.utils.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class Core extends JavaPlugin {

    private static Core INSTANCE;

    public static PartyManager partyManager;

    public static Color defaultLeatherColor;

    public PartyConfig partyConfig = new PartyConfig(this);

    public PortalConfig portalConfig = new PortalConfig(this);
    public static Location spawnLocation;

    public static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        defaultLeatherColor = this.getServer().getItemFactory().getDefaultLeatherColor();
        (new VaultHook()).createHooks();

        spawnLocation = new Location(Bukkit.getWorld("SiegeHub"), 70.5, 71, 3.5, 90, 0);
        protocolManager = ProtocolLibrary.getProtocolManager();
        this.getCommand("hub").setExecutor(new Hub());
        this.getCommand("discords").setExecutor(new Discord());
        this.getCommand("getItem").setExecutor(new GetItem());
        this.getCommand("spawn").setExecutor(new Spawn());
        this.getCommand("invsee").setExecutor(new Invsee());

        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerBanking.bankLevels.put(player, Bank.INSTANCE.getBankLevel(player));
            PlayerBanking.bankAmounts.put(player, Bank.INSTANCE.getBankAmount(player));
        }

        //partyManager = new PartyManager();

//        PaperCommandManager manager = new PaperCommandManager(this);
//        manager.registerCommand(new PartyCommand());
//        manager.getCommandCompletions().registerCompletion("partyMembers", c -> {
//            Party party = partyManager.getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<>();
//            for (UUID member : members) {
//                names.add(Bukkit.getPlayer(member).getName());
//            }
//            return names;
//        });
//
//        manager.getCommandCompletions().registerCompletion("partyMembersExcludingSelf", c -> {
//            Party party = partyManager.getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<>();
//            for (UUID member : members) {
//                if (member != c.getPlayer().getUniqueId()) names.add(Bukkit.getPlayer(member).getName());
//            }
//            return names;
//        });
//
//        manager.getCommandCompletions().registerCompletion("nonPartyMembers", c -> {
//            Party party = partyManager.getParty(c.getPlayer().getUniqueId());
//            List<UUID> members = party.getMembersRaw();
//            List<String> names = new ArrayList<>();
//            for (UUID member : members) {
//                names.add(Bukkit.getPlayer(member).getName());
//            }
//            List<String> allPlayerNames = Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName)
//                    .collect(Collectors.toList());
//
//            return allPlayerNames.stream().filter(p -> !(names.contains(p))).collect(Collectors.toList());
//        });
//
//        manager.getCommandCompletions().registerCompletion("openToInvite", c -> {
//            List<Player> notInParty = Bukkit.getOnlinePlayers().stream()
//                    .filter(p -> partyManager.getParty(p.getUniqueId()) == null).collect(Collectors.toList());
//
//            return notInParty.stream().map(Player::getName)
//                    .collect(Collectors.toList());
//        });
        // TODO: uncomment this when working on party command


        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new DamageIndicatorListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new StatGemListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        getServer().getPluginManager().registerEvents(new PortalEnterListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new CustomCraftingEvents(), this);
        getServer().getPluginManager().registerEvents(new MobDrops(), this);
        getServer().getPluginManager().registerEvents(new PlayerBanking(), this);

        getServer().getPluginManager().registerEvents(new SmokyBlacksmith(), this);
        getServer().getPluginManager().registerEvents(new MeraTransit(), this);
        getServer().getPluginManager().registerEvents(new ClemontBlacksmith(), this);
        getServer().getPluginManager().registerEvents(new SymoneCollector(), this);
        getServer().getPluginManager().registerEvents(new Dealer(), this);
        getServer().getPluginManager().registerEvents(new BenButcher(), this);
        getServer().getPluginManager().registerEvents(new RichardBanker(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
        getServer().getPluginManager().registerEvents(new StatChangeListener(), this);
        SmokyBlacksmith.resetItems();
        StatChangeListener.statBarDisplayTask();

        getServer().getPluginManager().registerEvents(new CustomItemKotlinListener(), this);
        getServer().getPluginManager().registerEvents(new VanillaCraftingListener(), this);
        new RegenerationTask().startRegenTask();

        CustomRecipe.Companion.registerAllRecipes();

//        CustomRecipe recipe = new CustomRecipe();
//        recipe.s1(Pebble.Companion.tier(1));
//        CustomRecipe.Companion.registerRecipe(recipe);
        Bukkit.getLogger().info(String.valueOf(CustomRecipe.Companion.getRecipes().size()));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        INSTANCE = null;
//        partyManager.saveAll();
    }

    /**
     * Method to get the plugin from other classes
     * You can use Core.plugin() in other classes to get the plugin instance
     *
     * @return The main plugin
     */
    public static Core plugin() {
        return INSTANCE;
    }

}
