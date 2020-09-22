package me.amitay.Parkour.Listeners;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.Party;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    private Parkour pl;

    public CommandListener(Parkour pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onCommandsListener(PlayerCommandPreprocessEvent e) {
        if (pl.parkourManager.isInParkour(e.getPlayer())) {
            String message = e.getMessage().toLowerCase();
            String[] array = message.split(" ");
            if (array.length == 2)
                if (!array[0].equalsIgnoreCase("/party") || !array[1].equalsIgnoreCase("chat")){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Utils.getFormattedText("&cYou can only use the commands /party chat, /msg and /r while in parkour"));
                    return;
                }
            if (!array[0].equalsIgnoreCase("/msg") && !array[0].equalsIgnoreCase("/r")){
                e.setCancelled(true);
                e.getPlayer().sendMessage(Utils.getFormattedText("&cYou can only use the commands /party chat, /msg and /r while in parkour"));
                return;
            }
        }
    }

}
