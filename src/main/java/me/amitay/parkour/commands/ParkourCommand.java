package me.amitay.parkour.commands;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.ItemBuilder;
import me.amitay.parkour.utils.Utils;
import me.amitay.parkour.utils.party.Party;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ParkourCommand implements CommandExecutor {


    private Parkour pl;
    private ItemStack Teams, Solo, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;
    private Gui guiModeSlection, guiSolo, guiTeams;
    private GuiItem guiTeamsItem, guiSoloItem,t1Gui, t2Gui, t3Gui, t4Gui, t5Gui, t6Gui, t7Gui, t8Gui, t9Gui, t10Gui, s1Gui, s2Gui, s3Gui, s4Gui, s5Gui, s6Gui, s7Gui, s8Gui, s9Gui, s10Gui;

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
        if (args.length != 0){
            p.sendMessage(Utils.getFormattedText(pl.ParkourPrefix + "&7Correct usage: /parkour"));
            return true;
        }
        if (!pl.partyManager.isInParty(p)){
            p.sendMessage(Utils.getFormattedText("&cYou must be in a party to use this command. (If you want to play parkour solo you need to be alone yet inside a party)"));
            return true;
        }
        loadMainInventory(p);
        p.openInventory(guiModeSlection.getInventory());
        return true;
    }

    private void loadMainInventory(Player p){
        p.openInventory(guiModeSlection.getInventory());
        guiTeamsItem = new GuiItem(Teams, inventoryClickEvent -> {
            if (inventoryClickEvent.getInventory().equals(guiModeSlection)) {
                if (!pl.partyManager.isInParty(p)) {
                    p.sendMessage(Utils.getFormattedText("&cYou can not play Teams mode alone, you must create a party and invite a friend, /party help for more information"));
                    inventoryClickEvent.setCancelled(true);
                    p.closeInventory();
                    return;
                }
                Party party = pl.partyManager.getPlayerParty(p);
                if (!party.hasMember()){
                    p.sendMessage(Utils.getFormattedText("&cYou can not play Teams mode alone, you must create a party and invite a friend, /party help for more information"));
                    inventoryClickEvent.setCancelled(true);
                    p.closeInventory();
                    return;
                }
                p.openInventory(guiTeams.getInventory());
                inventoryClickEvent.setCancelled(true);
            }
        });
        guiSoloItem = new GuiItem(Solo, inventoryClickEvent -> {
            if (inventoryClickEvent.getInventory().equals(guiModeSlection)) {
                if (!pl.partyManager.isInParty(p)) {
                    p.sendMessage(Utils.getFormattedText("&cYou can not play Solo mode with a party, /party leave to leave your current party and /party help for more information"));
                    inventoryClickEvent.setCancelled(true);
                    p.closeInventory();
                    return;
                }
                p.openInventory(guiSolo.getInventory());
                inventoryClickEvent.setCancelled(true);
            }
        });
    }


    public void loadItems(){
        Teams = new ItemBuilder(
                Material.WOOL, 1)
                .setDyeColor(DyeColor.GREEN)
                .setName(ChatColor.YELLOW + "Parkour teams").addLoreLine(ChatColor.YELLOW + "Click here to play parkour teams with your party member").toItemStack();
        Solo = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.BLUE).setName(ChatColor.YELLOW + "Parkour solo").addLoreLine(ChatColor.YELLOW + "Click here to play parkour alone").toItemStack();

        t1 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 1 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 1")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t2 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 2 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 2")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t3 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 3 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 3")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t4 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 4 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 4")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t5 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 5 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 5")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t6 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 6 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 6")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t7 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 7 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 7")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t8 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 8 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 8")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t9 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 9 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 9")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t10 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.RED).setName(ChatColor.YELLOW + "Level 10 -> REALLY HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 10")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();

        s1 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 1 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 1")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s2 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 2 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 2")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s3 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 3 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 3")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s4 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 4 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 4")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s5 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 5 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 5")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s6 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 6 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 6")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s7 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 7 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 7")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s8 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 8 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 8")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s9 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 9 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 9")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s10 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.RED).setName(ChatColor.YELLOW + "Level 10 -> REALLY HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 10")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        createAllInventories();
    }
    public void createAllInventories(){
        guiTeams = new Gui(pl, 27, Utils.getFormattedText("&aParkour Teams Levels"));
        guiSolo = new Gui(pl, 27, Utils.getFormattedText("&aParkour Solo Levels"));

        guiModeSlection = new Gui(pl, 27, Utils.getFormattedText("&aParkour Mode Selecion"));
        guiModeSlection.getInventory().setItem(12, guiSoloItem.getItem());
        guiModeSlection.getInventory().setItem(14, guiTeamsItem.getItem());
    }

    private void loadTeamsItemsClickEvents(Player p, Player member){
        t1Gui = new GuiItem(t1, inventoryClickEvent -> {
            partyTeamMethod(p, member, 1, inventoryClickEvent);
        });
        t2Gui = new GuiItem(t2, inventoryClickEvent -> {
            partyTeamMethod(p, member, 2, inventoryClickEvent);
        });
        t3Gui = new GuiItem(t3, inventoryClickEvent -> {
            partyTeamMethod(p, member, 3, inventoryClickEvent);
        });
        t4Gui = new GuiItem(t4, inventoryClickEvent -> {
            partyTeamMethod(p, member, 4, inventoryClickEvent);
        });
        t5Gui = new GuiItem(t5, inventoryClickEvent -> {
            partyTeamMethod(p, member, 5, inventoryClickEvent);
        });
        t6Gui = new GuiItem(t6, inventoryClickEvent -> {
            partyTeamMethod(p, member, 6, inventoryClickEvent);
        });
        t7Gui = new GuiItem(t7, inventoryClickEvent -> {
            partyTeamMethod(p, member, 7, inventoryClickEvent);
        });
        t8Gui = new GuiItem(t8, inventoryClickEvent -> {
            partyTeamMethod(p, member, 8, inventoryClickEvent);
        });
        t9Gui = new GuiItem(t9, inventoryClickEvent -> {
            partyTeamMethod(p, member, 9, inventoryClickEvent);
        });
        t10Gui = new GuiItem(t10, inventoryClickEvent -> {
            partyTeamMethod(p, member, 10, inventoryClickEvent);
        });

    }
    private void loadSoloItemsClickEvents(Player p){
        s1Gui = new GuiItem(s1, inventoryClickEvent -> {
            partySoloMethod(p, 1, inventoryClickEvent);
        });
        s2Gui = new GuiItem(s2, inventoryClickEvent -> {
            partySoloMethod(p, 2, inventoryClickEvent);
        });
        s3Gui = new GuiItem(s3, inventoryClickEvent -> {
            partySoloMethod(p, 3, inventoryClickEvent);
        });
        s4Gui = new GuiItem(s4, inventoryClickEvent -> {
            partySoloMethod(p, 4, inventoryClickEvent);
        });
        s5Gui = new GuiItem(s5, inventoryClickEvent -> {
            partySoloMethod(p, 5, inventoryClickEvent);
        });
        s6Gui = new GuiItem(s6, inventoryClickEvent -> {
            partySoloMethod(p, 6, inventoryClickEvent);
        });
        s7Gui = new GuiItem(s7, inventoryClickEvent -> {
            partySoloMethod(p, 7, inventoryClickEvent);
        });
        s8Gui = new GuiItem(s8, inventoryClickEvent -> {
            partySoloMethod(p, 8, inventoryClickEvent);
        });
        s9Gui = new GuiItem(s9, inventoryClickEvent -> {
            partySoloMethod(p, 9, inventoryClickEvent);
        });
        s10Gui = new GuiItem(s10, inventoryClickEvent -> {
            partySoloMethod(p, 10, inventoryClickEvent);
        });

    }

    private void partyTeamMethod(Player p, Player member, int lvl, InventoryClickEvent e){
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null){
            p.sendMessage(Utils.getFormattedText("&cYou need to create a party to play parkour, /party help for more information"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }
        if (party.getLeaderTeamLevel() <= 0 || party.getMemberTeamLevel() <= 0){
            p.sendMessage(Utils.getFormattedText("&cPlease retry the action, if that problem still occurs contact a staff member"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }
        if (party.getLeaderTeamLevel() >= lvl && party.getMemberTeamLevel() >= lvl){
            if (party.getLeaderTeamLevel() == 5) {
                System.out.println("Completed level " + lvl + " for first time");
                party.updateLeaderSoloLevel(p.getUniqueId());
            } else{
                System.out.println("That's not the first time (level " + lvl);
            }
            if (party.getLeaderSoloLevel() == lvl) {
                System.out.println("Completed level " + lvl + " for first time");
                party.updateLeaderSoloLevel(p.getUniqueId());
            } else{
                System.out.println("That's not the first time (level " + lvl);
            }
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        } else {
            p.sendMessage(Utils.getFormattedText("&cOne of the party members in not high enough level to do this parkour map"));
            member.sendMessage(Utils.getFormattedText("&cOne of the party members in not high enough level to do this parkour map"));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }
    private void partySoloMethod(Player p, int lvl, InventoryClickEvent e){
            Party party = pl.partyManager.getPlayerParty(p);
            if (party == null){
                p.sendMessage(Utils.getFormattedText("&cYou need to create a party to play parkour, /party help for more information"));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            if (party.getLeaderSoloLevel() <= lvl){
                p.sendMessage(Utils.getFormattedText("&cPlease retry the action, if that problem still occurs contact a staff member"));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            if (party.getLeaderSoloLevel() >= lvl) {
                if (party.getLeaderSoloLevel() == lvl) {
                    System.out.println("Completed level " + lvl + " for first time");
                    party.updateLeaderSoloLevel(p.getUniqueId());
                } else {
                    System.out.println("That's not the first time (level " + lvl);
                }
            } else {
                p.sendMessage(Utils.getFormattedText("&cOYou are not high enough level to do this parkour map"));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }
    }
}
