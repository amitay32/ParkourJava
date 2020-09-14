package me.amitay.Parkour.Listeners;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.ParkourPackage.ParkourStage;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private Parkour pl;
    private boolean shouldSay = true;

    public PlayerInteractListener(Parkour pl) {
        this.pl = pl;
    }

    @EventHandler
    private void playerInteractEvent(PlayerInteractEvent e) {
        if (pl.parkourManager.isInParkour(e.getPlayer())) {
            Player p = e.getPlayer();
            Party party = pl.partyManager.getPlayerParty(p);
            if (party == null) {
                return;
            }
            if (e.getAction().equals(Action.PHYSICAL)) {
                if (e.getClickedBlock().getType().equals(Material.IRON_PLATE)) {
                    ParkourStage parkourStage = new ParkourStage(party.getCurrentPlayedLevel(), party.getCurrentPlayedMode(), pl);
                    int checkpoint = -1;
                    for (int i = 0; i <= pl.getConfig().getList("Parkour.Levels." + party.getCurrentPlayedMode() + "." + party.getCurrentPlayedLevel() + ".CheckPoints").size() - 1; i++) {
                        Location loc = (Location) pl.getConfig().getList("Parkour.Levels." + party.getCurrentPlayedMode() + "." + party.getCurrentPlayedLevel() + ".CheckPoints").get(i);
                        if (e.getClickedBlock().getLocation().getBlockZ() == loc.getBlockZ() && e.getClickedBlock().getLocation().getBlockX() == loc.getBlockX() && e.getClickedBlock().getLocation().getBlockY() == loc.getBlockY()) {
                            checkpoint++;
                            break;
                        }
                        checkpoint++;
                    }
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                        @Override
                        public void run() {
                            if (shouldSay == false)
                                shouldSay = true;
                        }
                    }, 40L);
                    if (pl.parkourManager.getCheckPoint(e.getPlayer()) + 1 > checkpoint) {
                        if (shouldSay == true) {
                            p.sendMessage(Utils.getFormattedText("&eYou have already passed that checkpoint"));
                            shouldSay = false;
                        }
                    } else {
                        party.setCurrentCheckpoint(checkpoint);
                        if (shouldSay == true) {
                            p.sendMessage(Utils.getFormattedText("&eYou are now standing on the " + (pl.parkourManager.getCheckPoint(p) + 1) + "/" + (pl.getConfig().getList("Parkour.Levels." + party.getCurrentPlayedMode() + "." + party.getCurrentPlayedLevel() + ".CheckPoints").size()) + " checkpoint" +
                                    "for this parkour map. Use /parkour checkpoint to teleport to here"));
                            shouldSay = false;
                        }
                    }
                    if (party.getCurrentPlayedMode().equalsIgnoreCase("Teams")) {
                        if (pl.partyManager.isLeader(p)) {
                            if (shouldSay == true) {
                                party.getMember().sendMessage("&eYour teammate is now standing on a new checkpoint, /parkour checkpoint to teleport to them!");
                                shouldSay = false;
                            }
                        } else {
                            if (shouldSay == true) {
                                party.getLeader().sendMessage("&eYour teammate is now standing on a new checkpoint, /parkour checkpoint to teleport to them!");
                                shouldSay = false;
                            }
                        }
                    }
                }
                if (e.getClickedBlock().getType().equals(Material.GOLD_PLATE)) {
                    ParkourStage parkourStage = new ParkourStage(party.getCurrentPlayedLevel(), party.getCurrentPlayedMode(), pl);
                    Location loc = (Location) pl.getConfig().get("Parkour.Levels." + party.getCurrentPlayedMode() + "." + party.getCurrentPlayedLevel() + ".StartPoint");
                    if (e.getClickedBlock().getLocation().getBlockX() == loc.getBlockX() && e.getClickedBlock().getLocation().getBlockZ() == loc.getBlockZ()) {
                        if (shouldSay == true) {
                            p.sendMessage(Utils.getFormattedText("&eYou have started parkour stage number " + parkourStage.getStage() + " &eGood luck!"));
                            shouldSay = false;
                        }
                    }
                    Location loc1 = (Location) pl.getConfig().get("Parkour.Levels." + party.getCurrentPlayedMode() + "." + party.getCurrentPlayedLevel() + ".EndPoint");
                    if (e.getClickedBlock().getLocation().getBlockX() == loc1.getBlockX() && e.getClickedBlock().getLocation().getBlockZ() == loc1.getBlockZ()) {
                        if (shouldSay == true) {
                            p.sendMessage(Utils.getFormattedText("&eYou have finished parkour stage number " + parkourStage.getStage() + " &eGood job!"));
                            shouldSay = false;
                        }
                        pl.parkourManager.finishLevel(parkourStage, party);
                    }
                }
            }
        }
    }
}
