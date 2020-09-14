package me.amitay.Parkour;

import me.amitay.Parkour.Commands.ParkourCommand;
import me.amitay.Parkour.Commands.PartyCommands;
import me.amitay.Parkour.Listeners.OnJoinListener;
import me.amitay.Parkour.Listeners.PartyChatListener;
import me.amitay.Parkour.Listeners.PlayerInteractListener;
import me.amitay.Parkour.Listeners.QuitListener;
import me.amitay.Parkour.Utils.LoadItems;
import me.amitay.Parkour.Utils.MySql;
import me.amitay.Parkour.Utils.ParkourPackage.ParkourManager;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.InviteManager;
import me.amitay.Parkour.Utils.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Parkour extends JavaPlugin {

    public String prefix;
    public PartyManager partyManager = new PartyManager(this);
    public InviteManager inviteManager = new InviteManager(this);
    public LoadItems loadItemsClass = new LoadItems(this);
    public ParkourManager parkourManager = new ParkourManager(this);
    private final ParkourCommand parkourCommand = new ParkourCommand(this);
    public String ParkourPrefix = "&ePARKOUR >>";


    private MySql mysql;

    public void onEnable() {
        loadConfigData();
        loadUtils();
    }

    public void loadCommands() {
        getCommand("party").setExecutor(new PartyCommands(this));
        getCommand("parkour").setExecutor(new ParkourCommand(this));
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new PartyChatListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new OnJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ParkourCommand(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
    }

    public void loadUtils() {
        prefix = getConfig().getString(ChatColor.translateAlternateColorCodes('&', "Prefix"));
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        parkourManager.loadLocationsSolo();
        parkourManager.loadLocationsTeams();
        loadItemsClass.loadItems();
        mysql = new MySql(this);
    }

    public void loadConfigData() {
        saveDefaultConfig();
    }

    public MySql getMySql() {
        return mysql;
    }


}
