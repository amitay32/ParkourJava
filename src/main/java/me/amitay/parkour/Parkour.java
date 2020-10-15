package me.amitay.parkour;

import me.amitay.parkour.commands.ParkourCommand;
import me.amitay.parkour.commands.PartyCommands;
import me.amitay.parkour.listeners.OnJoinListener;
import me.amitay.parkour.listeners.PartyChatListener;
import me.amitay.parkour.listeners.QuitListener;
import me.amitay.parkour.utils.MySql;
import me.amitay.parkour.utils.party.InviteManager;
import me.amitay.parkour.utils.party.PartyManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Parkour extends JavaPlugin {

    public String prefix;
    public PartyManager partyManager = new PartyManager(this);
    public InviteManager inviteManager = new InviteManager(this);
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
    }

    public void loadUtils() {
        prefix = getConfig().getString(ChatColor.translateAlternateColorCodes('&', "Prefix"));
        loadCommands();
        loadEvents();
        saveDefaultConfig();
        mysql = new MySql(this);
        parkourCommand.loadItems();
    }

    public void loadConfigData() {
        saveDefaultConfig();
    }

    public MySql getMySql() {
        return mysql;
    }


}
