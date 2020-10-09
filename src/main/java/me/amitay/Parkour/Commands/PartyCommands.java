package me.amitay.Parkour.Commands;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommands implements CommandExecutor {

    private Parkour pl;

    public PartyCommands(Parkour pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This plugin is for Players only!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("create")) {
                if (pl.partyManager.isInParty(p)) {
                    p.sendMessage(Utils.getFormattedText("&cYou are already in a party"));
                    return true;
                }
                pl.partyManager.createParty(p, pl);
            }
            if (args[0].equalsIgnoreCase("leave")) {
                if (pl.partyManager.isInParty(p)) {
                    pl.partyManager.leaveParty(p);
                    return true;
                }
                p.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                if (pl.partyManager.isInParty(p)) {
                    pl.partyManager.getPartyMembers(p);
                    return true;
                }
                p.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
                return true;
            }
            if (args[0].equalsIgnoreCase("chat")) {
                if (pl.partyManager.isInParty(p)) {
                    Party party = pl.partyManager.getPlayerParty(p);
                    if (!party.hasMember()){
                        p.sendMessage(Utils.getFormattedText("&cYou must have at least 2 members in your party to use party chat"));
                        return true;
                    }
                    if (pl.partyManager.isLeader(p)) {
                        if (party.partyChatEnabledLeader()) {
                            p.sendMessage(Utils.getFormattedText("&eYou are no longer using party chat!"));
                            party.setPartyChatLeader();
                            return true;
                        }
                        p.sendMessage(Utils.getFormattedText("&eYou are now using party chat!"));
                        party.setPartyChatLeader();
                        return true;
                    }
                    if (party.partyChatEnabledMemebr()) {
                        p.sendMessage(Utils.getFormattedText("&eYou are no longer using party chat!"));
                        party.setPartyChatPlayer();
                        return true;
                    }
                    party.setPartyChatPlayer();
                    p.sendMessage(Utils.getFormattedText("&eYou are now using party chat!"));
                    return true;

                }
                p.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
                return true;
            }
            if (args[0].equalsIgnoreCase("invite")) {
                p.sendMessage(Utils.getFormattedText("&cCorrect usage: /party invite <name>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("join")) {
                p.sendMessage(Utils.getFormattedText("&cCorrect usage: /party join <name>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("deny")) {
                p.sendMessage(Utils.getFormattedText("&cCorrect usage: /party deny <name>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("promote")) {
                p.sendMessage(Utils.getFormattedText("&cCorrect usage: /party promote <name>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("help")){
                sendMessages(p);
                return true;
            }
            sendMessages(p);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("invite")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    if (pl.partyManager.isInParty(p)) {
                        Party party = pl.partyManager.getPlayerParty(p);
                        if (p == null) {
                            return true;
                        }
                        if (!pl.partyManager.isLeader(p)) {
                            p.sendMessage(Utils.getFormattedText("&cOnly the party leader can invite new people to the party"));
                            return true;
                        }
                        if (party.hasMember()) {
                            p.sendMessage(Utils.getFormattedText("&cYour party is already full"));
                            return true;
                        }
                        pl.inviteManager.invitePlayer(p.getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId());

                    } else {
                        p.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
                        return true;
                    }
                } else {
                    p.sendMessage(Utils.getFormattedText("&cThat player is currently not online"));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    pl.inviteManager.acceptInvite(Bukkit.getPlayer(args[1]).getUniqueId(), p.getUniqueId());
                    return true;
                }
                p.sendMessage(Utils.getFormattedText("&cThat player is currently not online"));
                return true;
            }
            if (args[0].equalsIgnoreCase("deny")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    pl.inviteManager.denyInvite(Bukkit.getPlayer(args[1]).getUniqueId(), p.getUniqueId());
                    return true;
                }
                p.sendMessage(Utils.getFormattedText("&cThat player is currently not online"));
                return true;
            }
            if (args[0].equalsIgnoreCase("promote")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    pl.partyManager.promoteMember(Bukkit.getPlayer(args[1]), p);
                    return true;
                }
                p.sendMessage(Utils.getFormattedText("&cThat player is currently not online"));
                return true;
            } else {
                sendMessages(p);
                return true;
            }
        } else if (args.length == 0){
            sendMessages(p);
        } else {
            sendMessages(p);
            return true;
        }
        return true;
    }
    public void sendMessages(Player p){
        p.sendMessage(Utils.getFormattedText("&7--- &aParkour party system help menu &7---"));
        p.sendMessage(Utils.getFormattedText("&e/party create &f>> &7Creates a new party"));
        p.sendMessage(Utils.getFormattedText("&e/party invite <name> &f>> &7Invites a new member to your party"));
        p.sendMessage(Utils.getFormattedText("&e/party join <name> &f>> &7Joins a party"));
        p.sendMessage(Utils.getFormattedText("&e/party deny <name> &f>> &7Denies a party invitation"));
        p.sendMessage(Utils.getFormattedText("&e/party leave &f>> &7Leaves a party"));
        p.sendMessage(Utils.getFormattedText("&e/party chat &f>> &7Enables party chat mode"));
        p.sendMessage(Utils.getFormattedText("&e/party list &f>>&7Displays the current members in your party"));
        p.sendMessage(Utils.getFormattedText("&e/party promote <name> &f>> &7Promotes a player to a party leader"));
        p.sendMessage(Utils.getFormattedText("&e/party help &f>> &7Displays this menu"));
    }
}
