package me.amitay.parkour.listeners;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.Utils;
import me.amitay.parkour.utils.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PartyChatListener implements Listener {

    private Parkour pl;
    private String prefix = "&9&lParty &8&l> ";

    public PartyChatListener(Parkour pl) {
        this.pl = pl;
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if (message.startsWith("/"))
            return;
        Player p = e.getPlayer();
        if (pl.partyManager.isInParty(p)) {
            Party party = pl.partyManager.getPlayerParty(p);
            if (party == null)
                return;
            if (party.hasMember()) {
                if (pl.partyManager.isLeader(p)) {
                    if (party.partyChatEnabledLeader()) {
                        p.sendMessage(Utils.getFormattedText(prefix + "&f" + p.getName() + ": " + message));
                        party.getMember().sendMessage(Utils.getFormattedText(prefix + "&f" + p.getName() + ": " + message));
                        e.setCancelled(true);
                        return;
                    }
                }
                if (party.partyChatEnabledMemebr()) {
                    p.sendMessage(Utils.getFormattedText(prefix + "&f" + p.getName() + ": " + message));
                    party.getLeader().sendMessage(Utils.getFormattedText(prefix + "&f" + p.getName() + ": " + message));
                    e.setCancelled(true);
                    return;
                }
            }
        }

    }
}

