package me.amitay.parkour.listeners;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.Utils;
import me.amitay.parkour.utils.party.Party;
import org.bukkit.Bukkit;
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
            if (party.getLeader() != null && party.getLeader().equals(p)) {// handle if leader quits
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getLeader().getName());
                if (party.getMember() != null) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getMember().getName());
                    party.getMember().sendMessage(Utils.getFormattedText("&eOne of the party members has left the game, therefor the party was concluded"));
                }
            }
            if (party.getMember() != null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getMember().getName());
                if (party.getLeader() != null) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getLeader().getName());
                    party.getMember().sendMessage("&eOne of the party members has left the game, therefor the party was concluded");
                }
            }
            party.deleteParty();
            pl.partyManager.getParties().remove(party);
        }
    }
}


