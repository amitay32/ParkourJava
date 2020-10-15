package me.amitay.parkour;

import me.amitay.parkour.commands.ParkourCommand;
import me.amitay.parkour.commands.PartyCommands;
import me.amitay.parkour.utils.LoadItems;
import me.amitay.parkour.utils.MySql;
import me.amitay.parkour.utils.ParkourPackage.ParkourManager;
import me.amitay.parkour.utils.party.InviteManager;
import me.amitay.parkour.utils.party.PartyManager;
import me.amitay.parkour.listeners.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Parkour extends JavaPlugin {

    public String prefix;
    public PartyManager partyManager;
    public InviteManager inviteManager;
    public LoadItems loadItemsClass;
    public ParkourManager parkourManager;
    public String ParkourPrefix = "&ePARKOUR >>";
    private MySql mysql;

    public void onEnable() {
        loadUtils();
    }

    public void loadCommands() {
        getCommand("party").setExecutor(new PartyCommands(this));
        getCommand("parkour").setExecutor(new ParkourCommand(this));
    }
    private void loadManagers(){
        partyManager = new PartyManager(this);
        inviteManager = new InviteManager(this);
        loadItemsClass = new LoadItems(this);
        parkourManager = new ParkourManager(this);
    }
    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new PartyChatListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new OnJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ParkourCommand(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
    }
    public void loadUtils() {
        saveDefaultConfig();
        loadManagers();
        loadCommands();
        loadEvents();
        prefix = getConfig().getString(ChatColor.translateAlternateColorCodes('&', "Prefix"));
        loadItemsClass.loadItems();
        mysql = new MySql(this);
    }

    public MySql getMySql() {
        return mysql;
    }


}
