package me.amitay.parkour.listeners;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.Utils;
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
            if (array[0].equalsIgnoreCase("/msg") || array[0].equalsIgnoreCase("/r"))
                return;
            if (array.length == 2) {
                if (array[0].equalsIgnoreCase("/party") && array[1].equalsIgnoreCase("chat"))
                    return;
                if (array[0].equalsIgnoreCase("/parkour") && array[1].equalsIgnoreCase("checkpoint"))
                    return;
                if (array[0].equalsIgnoreCase("/parkour") && array[1].equalsIgnoreCase("debug"))
                    return;
            }
            e.getPlayer().sendMessage(Utils.getFormattedText("&cYou can only use the commands /party chat, /msg, /r and /parkour checkpoint while in parkour"));
            e.setCancelled(true);
        }
    }
}
