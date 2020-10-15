package me.amitay.parkour.utils.party;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.Utils;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InviteManager {
    public Map<UUID, Invites> invites = new HashMap<>();
    private Parkour pl;

    public InviteManager(Parkour pl) {
        this.pl = pl;
    }

    public void invitePlayer(UUID invitor, UUID invited) {
        if (invitor.equals(invited)){
            Bukkit.getPlayer(invitor).sendMessage(Utils.getFormattedText("&cYou can not invite yourself to the party"));
            return;
        }
        if (invites.containsKey(invited)) {
            if (invites.get(invited).timer.containsKey(invitor)) {
                if (invites.get(invited).timer.get(invitor) + 60000 >= System.currentTimeMillis()) {
                    Bukkit.getPlayer(invitor).sendMessage(Utils.getFormattedText("&cYou have already invited that player to a party"));
                    return;
                }
                invites.get(invited).timer.put(invitor, System.currentTimeMillis());
            }
            invites.get(invited).timer.put(invitor, System.currentTimeMillis());
        }
        invites.put(invited, new Invites(invitor));
        Bukkit.getPlayer(invitor).sendMessage(Utils.getFormattedText("&eYou have successfully invited " + Bukkit.getPlayer(invited).getName() + " to your party. They have 60 seconds to accept your invitation"));
        Bukkit.getPlayer(invited).sendMessage(Utils.getFormattedText("&eYou have being invited to "
                + Bukkit.getPlayer(invitor).getName() + " party. /party join " + Bukkit.getPlayer(invitor).getName()) + " to join");
        return;
    }

    public void acceptInvite(UUID invitor, UUID joiner) {
        if (invites.containsKey(joiner)) {
            if (invites.get(joiner).timer.containsKey(invitor)) {
                if (invites.get(joiner).timer.get(invitor) + 60000 <= System.currentTimeMillis()) {
                    Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&cThat party invite is already expired"));
                    invites.get(joiner).timer.remove(invitor);
                    invites.remove(joiner);
                    return;
                }
                Party party = pl.partyManager.getPlayerParty(Bukkit.getPlayer(invitor));
                if (!party.hasMember()) {
                    party.addmember(Bukkit.getPlayer(joiner));
                    Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&eYou have successfully joined " + Bukkit.getPlayer(invitor).getName() + " party"));
                    Bukkit.getPlayer(invitor).sendMessage(Utils.getFormattedText("&e" + "" + Bukkit.getPlayer(joiner).getName() + " Has joined your party"));
                }
            } else {
                Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&cYou were not invited to that party"));
                return;
            }
        } else {
            Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&cYou were not invited to that party"));
            return;
        }
    }

    public void denyInvite(UUID invitor, UUID joiner) {
        if (invites.containsKey(joiner)) {
            if (invites.get(joiner).timer.containsKey(invitor)) {
                if (invites.get(joiner).timer.get(invitor) + 60000 <= System.currentTimeMillis()) {
                    Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&cThat party invite is already expired"));
                    invites.get(joiner).timer.remove(invitor);
                    invites.remove(joiner);
                    return;
                }
                invites.remove(joiner);
                Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&eYou have denied " + Bukkit.getPlayer(invitor).getName() + "party invitation"));
                Bukkit.getPlayer(invitor).sendMessage(Utils.getFormattedText("&e" + "" + Bukkit.getPlayer(joiner).getName() + " Has denied your party invitation"));
                return;
            }
        } else {
            Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&cYou were not invited to that party"));
            return;
        }
        Bukkit.getPlayer(joiner).sendMessage(Utils.getFormattedText("&cYou were not invited to that party"));
        return;
    }
}

