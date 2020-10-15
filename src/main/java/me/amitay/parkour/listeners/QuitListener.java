package me.amitay.parkour.listeners;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.Utils;
import me.amitay.parkour.utils.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    private Parkour pl;

    public QuitListener(Parkour pl) {
        this.pl = pl;
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (pl.partyManager.isInParty(p)) {
            Party party = pl.partyManager.getPlayerParty(p);
            if (party == null) {
                return;
            }
            if (party.getLeader() != null) {
                party.getLeader().sendMessage(Utils.getFormattedText("&c" + p.getName() + " Has left the server, therefore the party was disbanded"));
            }
            else if (party.getMember() != null) {
                party.getLeader().sendMessage(Utils.getFormattedText("&c" + party.getLeaderName() + " Has left the server, therefore the party was disbanded"));
            }
            party.deleteParty();
            pl.partyManager.getParties().remove(party);
            pl.inviteManager.invites.remove(p);
            return;
        }
    }
}

