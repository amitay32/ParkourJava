package me.amitay.parkour.commands;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.ParkourPackage.CheckPoint;
import me.amitay.parkour.utils.ParkourPackage.ParkourStage;
import me.amitay.parkour.utils.Utils;
import me.amitay.parkour.utils.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Map;

public class ParkourCommand implements CommandExecutor, Listener {


    private Parkour pl;
    private Inventory guiModeSlection, guiSolo, guiTeams;

    public ParkourCommand(Parkour pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This plugin is for Players only!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length != 0) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("checkpoint")) {
                    Party party = pl.partyManager.getPlayerParty(p);
                    if (pl.parkourManager.isInParkour(p)) {
                        if (party.getCurrentCheckpoint() != null) {
                            p.teleport(party.getCurrentCheckpoint());
                            p.sendMessage(Utils.getFormattedText("&eSuccessfully teleported to last checkpoint"));
                            return true;
                        }
                        p.sendMessage(Utils.getFormattedText("&cYou did not step on any checkpoints yet"));
                        return true;

                    }
                }
                if (args[0].equalsIgnoreCase("debug")) {
                    System.out.println("debug command");
                    Party party = pl.partyManager.getPlayerParty(p);
                    ParkourStage stage = pl.parkourManager.getParkourStage(party.getCurrentPlayedLevel(), party.getCurrentPlayedMode());
                    if (stage.getCheckPointMap().get(p.getWorld()) == null) {
                        System.out.println("Black boolbool");
                        return true;
                    }
                    for (Map.Entry<World, CheckPoint> entry : stage.getCheckPointMap().entrySet()) {
                        System.out.println(entry.getKey().getName());
                        System.out.println("ParkourCommand.onCommand " + entry.getValue() == null);
                    }
                    for (Map.Entry<Integer, Location> entry : stage.getCheckPointMap().get(p.getWorld()).getCheckPoints().entrySet()) {
                        System.out.println("CommandListener.onCommandsListener " + entry.getKey() + " at " + entry.getValue().toString());
                    }
                    return true;
                }
            }
            if (args.length == 4) {
                if (p.hasPermission("Parkour.Admin")) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("startpoint")) {
                            if (isInteger(args[2])) {
                                if (args[3].equalsIgnoreCase("Teams")) {
                                    pl.getConfig().set("Parkour.Levels.Teams." + args[2] + ".StartPoint", p.getLocation());
                                    pl.saveConfig();
                                    p.sendMessage(Utils.getFormattedText("&eSuccessfully set startpoint for teams level " + args[2]));
                                    return true;
                                }
                                if (args[3].equalsIgnoreCase("Solo")) {
                                    pl.getConfig().set("Parkour.Levels.Solo." + args[2] + ".StartPoint", p.getLocation());
                                    pl.saveConfig();
                                    p.sendMessage(Utils.getFormattedText("&eSuccessfully set startpoint for solo level " + args[2]));
                                    return true;
                                }
                            }
                        }
                        if (args[1].equalsIgnoreCase("checkpoint")) {
                            if (args[3].equalsIgnoreCase("Teams")) {
                                if (pl.getConfig().getList("Parkour.Levels.Teams." + args[2] + ".CheckPoints") == null) {
                                    ArrayList<Location> list = new ArrayList<>();
                                    list.add(p.getLocation());
                                    pl.getConfig().set("Parkour.Levels.Teams." + args[2] + ".CheckPoints", list);
                                    pl.saveConfig();
                                    p.sendMessage(Utils.getFormattedText("&eSuccessfully set checkpoint for teams level " + args[2]));
                                    return true;
                                }
                                ArrayList<Location> list = new ArrayList<>();
                                for (int i = 0; i < pl.getConfig().getList("Parkour.Levels.Teams." + args[2] + ".CheckPoints").size(); i++) {
                                    list.add((Location) pl.getConfig().getList("Parkour.Levels.Teams." + args[2] + ".CheckPoints").get(i));
                                }
                                list.add(p.getLocation());
                                pl.getConfig().set("Parkour.Levels.Teams." + args[2] + ".CheckPoints", list);
                                pl.saveConfig();
                                p.sendMessage(Utils.getFormattedText("&eSuccessfully set checkpoint for teams level " + args[2]));
                                return true;
                            }
                            if (args[3].equalsIgnoreCase("Solo")) {
                                if (pl.getConfig().getList("Parkour.Levels.Solo." + args[2] + ".CheckPoints") == null) {
                                    ArrayList<Location> list = new ArrayList<>();
                                    list.add(p.getLocation());
                                    pl.getConfig().set("Parkour.Levels.Solo." + args[2] + ".CheckPoints", list);
                                    pl.saveConfig();
                                    p.sendMessage(Utils.getFormattedText("&eSuccessfully set checkpoint for solo level " + args[2]));
                                    return true;
                                }
                                ArrayList<Location> list = new ArrayList<>();
                                for (int i = 0; i < pl.getConfig().getList("Parkour.Levels.Solo." + args[2] + ".CheckPoints").size(); i++) {
                                    list.add((Location) pl.getConfig().getList("Parkour.Levels.Solo." + args[2] + ".CheckPoints").get(i));
                                }
                                list.add(p.getLocation());
                                pl.getConfig().set("Parkour.Levels.Solo." + args[2] + ".CheckPoints", list);
                                pl.saveConfig();
                                p.sendMessage(Utils.getFormattedText("&eSuccessfully set checkpoint for soloq level " + args[2]));
                                return true;
                            }
                        }
                        if (args[1].equalsIgnoreCase("endpoint")) {
                            if (args[3].equalsIgnoreCase("Teams")) {
                                pl.getConfig().set("Parkour.Levels.Teams." + args[2] + ".EndPoint", p.getLocation());
                                pl.saveConfig();
                                p.sendMessage(Utils.getFormattedText("&eSuccessfully set endpoint for teams level " + args[2]));
                                return true;
                            }
                            if (args[3].equalsIgnoreCase("Solo")) {
                                pl.getConfig().set("Parkour.Levels.Solo." + args[2] + ".EndPoint", p.getLocation());
                                pl.saveConfig();
                                p.sendMessage(Utils.getFormattedText("&eSuccessfully set endpoint for solo level " + args[2]));
                            }
                        }
                    }
                }
            }
            return true;
        }
        if (!pl.partyManager.isInParty(p)) {
            p.sendMessage(Utils.getFormattedText("&cYou must be in a party to use this command. (If you want to play parkour solo you need to be alone yet inside a party)"));
            return true;
        }
        createMainGUI(p);
        return true;
    }


    private void createMainGUI(Player p) {
        guiModeSlection = Bukkit.createInventory(p, 27, Utils.getFormattedText("&aParkour Mode Selection"));
        guiModeSlection.setItem(12, pl.loadItemsClass.solo);
        guiModeSlection.setItem(14, pl.loadItemsClass.teams);
        p.openInventory(guiModeSlection);
    }

    private void loadTeamsInventory(Player p) {
        guiTeams = Bukkit.createInventory(p, 45, Utils.getFormattedText("&aParkour Teams Mode"));
        guiTeams.setItem(9, pl.loadItemsClass.t1);
        guiTeams.setItem(10, pl.loadItemsClass.t2);
        guiTeams.setItem(11, pl.loadItemsClass.t3);
        guiTeams.setItem(12, pl.loadItemsClass.t4);
        guiTeams.setItem(13, pl.loadItemsClass.t5);
        guiTeams.setItem(14, pl.loadItemsClass.t6);
        guiTeams.setItem(15, pl.loadItemsClass.t7);
        guiTeams.setItem(16, pl.loadItemsClass.t8);
        guiTeams.setItem(17, pl.loadItemsClass.t9);
        guiTeams.setItem(31, pl.loadItemsClass.t10);
        p.openInventory(guiTeams);
    }

    private void loadSoloInventory(Player p) {
        guiSolo = Bukkit.createInventory(p, 45, Utils.getFormattedText("&aParkour Solo Mode"));
        guiSolo.setItem(9, pl.loadItemsClass.s1);
        guiSolo.setItem(10, pl.loadItemsClass.s2);
        guiSolo.setItem(11, pl.loadItemsClass.s3);
        guiSolo.setItem(12, pl.loadItemsClass.s4);
        guiSolo.setItem(13, pl.loadItemsClass.s5);
        guiSolo.setItem(14, pl.loadItemsClass.s6);
        guiSolo.setItem(15, pl.loadItemsClass.s7);
        guiSolo.setItem(16, pl.loadItemsClass.s8);
        guiSolo.setItem(17, pl.loadItemsClass.s9);
        guiSolo.setItem(31, pl.loadItemsClass.s10);
        p.openInventory(guiSolo);
    }

    @EventHandler
    private void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getInventory().getName().equalsIgnoreCase(Utils.getFormattedText("&aParkour Mode Selection"))) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().equals(pl.loadItemsClass.teams)) {
                if (pl.partyManager.isInParty(p)) {
                    Party party = pl.partyManager.getPlayerParty(p);
                    if (party.hasMember()) {
                        e.setCancelled(true);
                        loadTeamsInventory(p);
                    } else {
                        e.setCancelled(true);
                        p.sendMessage(Utils.getFormattedText("&cYou must invite another member to your party to play the Teams mode"));
                    }
                } else {
                    e.setCancelled(true);
                    p.sendMessage(Utils.getFormattedText("&cYou must be in a party to play parkour, /party help for more information"));
                }
            } else if (e.getCurrentItem().equals(pl.loadItemsClass.solo)) {
                if (pl.partyManager.isInParty(p)) {
                    ;
                    Party party = pl.partyManager.getPlayerParty(p);
                    if (!party.hasMember()) {
                        e.setCancelled(true);
                        loadSoloInventory(p);
                    } else {
                        p.sendMessage(Utils.getFormattedText("&cYou can not play the solo parkour mode while there's another member in your party."));
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                    p.sendMessage(Utils.getFormattedText("&cYou must be in a party to play parkour, /party help for more information"));
                }
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(Utils.getFormattedText("&aParkour Teams Mode"))) {
            Player p = (Player) e.getWhoClicked();
            Party party = pl.partyManager.getPlayerParty(p);
            if (party == null) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                p.sendMessage(Utils.getFormattedText("&cAn error has accoured, please contact a staff member about it."));
                return;
            }
            if (!party.hasMember()) {
                e.setCancelled(true);
                p.sendMessage(Utils.getFormattedText("&cYou must invite another member to your party to play the Teams mode"));
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t1)) {
                partyTeamMethod(p, party.getMember(), 1, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t2)) {
                partyTeamMethod(p, party.getMember(), 2, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t3)) {
                partyTeamMethod(p, party.getMember(), 3, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t4)) {
                partyTeamMethod(p, party.getMember(), 4, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t5)) {
                partyTeamMethod(p, party.getMember(), 5, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t6)) {
                partyTeamMethod(p, party.getMember(), 6, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t7)) {
                partyTeamMethod(p, party.getMember(), 7, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t8)) {
                partyTeamMethod(p, party.getMember(), 8, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t9)) {
                partyTeamMethod(p, party.getMember(), 9, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.t10)) {
                partyTeamMethod(p, party.getMember(), 10, e);
            }
        }
        if (e.getInventory().getName().equalsIgnoreCase(Utils.getFormattedText("&aParkour Solo Mode"))) {
            Player p = (Player) e.getWhoClicked();
            Party party = pl.partyManager.getPlayerParty(p);
            if (party == null) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                p.sendMessage(Utils.getFormattedText("&cAn error has accoured, please contact a staff member about it."));
                return;
            }
            if (party.hasMember()) {
                p.sendMessage(Utils.getFormattedText("&cYou can not play the solo parkour mode while there's another member in your party."));
                e.setCancelled(true);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s1)) {
                partySoloMethod(p, 1, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s2)) {
                partySoloMethod(p, 2, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s3)) {
                partySoloMethod(p, 3, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s4)) {
                partySoloMethod(p, 4, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s5)) {
                partySoloMethod(p, 5, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s6)) {
                partySoloMethod(p, 6, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s7)) {
                partySoloMethod(p, 7, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s8)) {
                partySoloMethod(p, 8, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s9)) {
                partySoloMethod(p, 9, e);
            }
            if (e.getCurrentItem().equals(pl.loadItemsClass.s10)) {
                partySoloMethod(p, 10, e);
            }
        }
    }

    private void partyTeamMethod(Player p, Player member, int lvl, InventoryClickEvent e) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null) {
            p.sendMessage(Utils.getFormattedText("&cYou need to create a party to play parkour, /party help for more information"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }
        if (party.getLeaderTeamLevel() <= 0 || party.getMemberTeamLevel() <= 0) {
            p.sendMessage(Utils.getFormattedText("&cPlease retry the action, if that problem still occurs contact a staff member"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }
        //4 >= 5
        if (party.getLeaderTeamLevel() >= lvl - 1 && party.getMemberTeamLevel() >= lvl - 1) {
            ParkourStage parkourStage = pl.parkourManager.getParkourStage(lvl, "Teams");
            party.setCurrentPlayedLevel(lvl);
            party.setCurrentPlayedMode("Teams");
            pl.parkourManager.startParkourLevel(p, parkourStage);
            return;
        }
        p.sendMessage(Utils.getFormattedText("&cOne of the party members in not high enough level to do this parkour map"));
        member.sendMessage(Utils.getFormattedText("&cOne of the party members in not high enough level to do this parkour map"));
        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
    }

    private void partySoloMethod(Player p, int lvl, InventoryClickEvent e) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null) {
            p.sendMessage(Utils.getFormattedText("&cYou need to create a party to play parkour, /party help for more information"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }
        if (party.getLeaderSoloLevel() <= 0) {
            p.sendMessage(Utils.getFormattedText("&cPlease retry the action, if that problem still occurs contact a staff member"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }
        if (party.getLeaderSoloLevel() >= lvl - 1) {
            ParkourStage parkourStage = pl.parkourManager.getParkourStage(lvl, "Solo");
            party.setCurrentPlayedLevel(lvl);
            party.setCurrentPlayedMode("Solo");
            pl.parkourManager.startParkourLevel(p, parkourStage);
            return;
        }
        p.sendMessage(Utils.getFormattedText("&cYou are not high enough level to do this parkour map"));
        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}

