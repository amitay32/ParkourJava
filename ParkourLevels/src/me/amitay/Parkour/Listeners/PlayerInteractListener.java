package me.amitay.Parkour.Listeners;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.ParkourPackage.CheckPoint;
import me.amitay.Parkour.Utils.ParkourPackage.ParkourStage;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
            Block block = p.getLocation().subtract(0, 1, 0).getBlock();
            if (block != null && e.getAction().equals(Action.PHYSICAL)) {
                World world = p.getLocation().getWorld();
                ParkourStage parkourStage = pl.parkourManager.getParkourStage(party.getCurrentPlayedLevel(), party.getCurrentPlayedMode());
                CheckPoint checkPoint = parkourStage.getCheckPoint(world);
                if (block.getType().equals(Material.IRON_PLATE)) {
                    if (party.getCurrentCheckpoint() == null) {
                        party.setCheckPoint(1, checkPoint.getLocationByStage(1), block);
                        return;
                    }
                    //In case of a bug inspect this part. (Event.getclickedblock could cause bugs to has stepped on)
                    if (!party.hasSteppedOn(block)) {
                        party.setCheckPoint(party.getCurrentCheckPointLevel() + 1, checkPoint.getLocationByStage(party.getCurrentCheckPointLevel() + 1), block);
                        party.getLeader().sendMessage(Utils.getFormattedText("&eYou can now use /parkour checkpoint to teleport to the next checkpoint!"));
                        if (party.getMember() != null)
                            party.getLeader().sendMessage(Utils.getFormattedText("&eYou can now use /parkour checkpoint to teleport to the next checkpoint!"));
                        return;
                    }//check if block
                    //handle command event while in parkour && fix quit listener tp
                    if (block.getType().equals(Material.GOLD_PLATE)) {
                        Block block1 = parkourStage.getEndPoint(p.getWorld()).subtract(0,1,0).getBlock();
                        if (block1 != null && block1.equals(block)){

                        }
                    }
                }
            }
        }
    }
}
