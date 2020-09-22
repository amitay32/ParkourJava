package me.amitay.Parkour.Utils.ParkourPackage;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkourManager {
    private Parkour pl;
    private ArrayList<ParkourStage> parkourStages = new ArrayList<>();

    //go over finishLevel

    public ParkourManager(Parkour pl) {
        this.pl = pl;
        List<World> worldList = new ArrayList<>();
        pl.getConfig().getStringList("worlds").forEach((key) ->
                worldList.add(Bukkit.getWorld(key))
        );
        Map<Integer, Location> checkPoints;
        if (pl.getConfig().getConfigurationSection("Parkour.Levels.Solo").getKeys(false) != null)
            for (String key : pl.getConfig().getConfigurationSection("Parkour.Levels.Solo").getKeys(false)) {
                try {
                    List<String> commands = pl.getConfig().getStringList("Parkour.Levels.Solo." + key + ".Commands");
                    checkPoints = loadCheckPoints(Integer.parseInt(key), "Solo");
                    ParkourStage stage = new ParkourStage(Integer.parseInt(key), "Solo", pl, worldList, 10000, checkPoints, commands);
                    parkourStages.add(stage);
                } catch (NullPointerException e) {
                }
            }
        if (pl.getConfig().getConfigurationSection("Parkour.Levels.Teams").getKeys(false) != null)
            for (String key : pl.getConfig().getConfigurationSection("Parkour.Levels.Teams").getKeys(false)) {
                try {
                    List<String> commands = pl.getConfig().getStringList("Parkour.Levels.Teams." + key + ".Commands");
                    checkPoints = loadCheckPoints(Integer.parseInt(key), "Teams");
                    ParkourStage stage = new ParkourStage(Integer.parseInt(key), "Teams", pl, worldList, 10000, checkPoints, commands);
                    parkourStages.add(stage);
                } catch (NullPointerException e) {
                }
            }
    }


    public ParkourStage getParkourStage(int level, String mode) {
        for (ParkourStage stage : parkourStages) {
            if (stage.getStage() == level && stage.getMode().equalsIgnoreCase(mode)) {
                return stage;
            }
        }
        return null;
    }

    public Map<Integer, Location> loadCheckPoints(int level, String mode) {
        Map<Integer, Location> list = new HashMap<>();
        for (int i = 0; i <= pl.getConfig().getList("Parkour.Levels." + mode + "." + level + ".CheckPoints").size() - 1; i++)
            list.putIfAbsent(i, (Location) pl.getConfig().getList("Parkour.Levels." + mode + "." + level + ".CheckPoints").get(i));
        return list;
    }


    public void startParkourLevel(Player p, ParkourStage parkour) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null) {
            p.sendMessage(Utils.getFormattedText("&cYou need to create a party to play parkour, /party help for more information"));
            return;
        }
        if (!parkour.playable()) {
            p.sendMessage(Utils.getFormattedText("&cAn error has occurred, please contact a staff member if that keeps on happening"));
            return;
        }
        if (parkour.getEmptyWorld() == null) {
            p.sendMessage(Utils.getFormattedText("&cThere are currently no empty worlds to play at, please wait a few minutes and try again."));
            return;
        }
        if (parkour.getMode().equalsIgnoreCase("Teams")) {
            if (party.hasMember()) {
                Player member = party.getMember();
                Player leader = party.getLeader();
                member.teleport(parkour.getStartPoint(parkour.getEmptyWorld()));
                leader.teleport(parkour.getStartPoint(parkour.getEmptyWorld()));
                party.startParkour(parkour.getStage(), parkour.getMode());
            }
            return;
        }
        if (parkour.getMode().equalsIgnoreCase("Solo")) {
            Player leader = party.getLeader();
            leader.teleport(parkour.getStartPoint(parkour.getEmptyWorld()));
            party.startParkour(parkour.getStage(), parkour.getMode());
            return;
        }
        p.sendMessage(Utils.getFormattedText("&cAn error has occurred, please contact a staff member if that keeps on happening"));
    }

    public boolean isInParkour(Player p) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null)
            return false;
        return party.getCurrentPlayedLevel() != -1;
    }


    public void finishLevel(ParkourStage stage, Party party) { //teleport them to spawn
        if (stage.getMode().equalsIgnoreCase("Solo")) {
            if (party.getLeaderSoloLevel() == stage.getStage()) {
                System.out.println("Completed level " + stage.getStage() + " for first time"); //go over it
                party.updateLeaderSoloLevel(party.getLeader().getUniqueId());
            } else {
                System.out.println("That's not the first time (level " + stage.getStage());
            }
            party.setCurrentPlayedMode(null);
            party.setCurrentPlayedLevel(-1);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getLeader().getName());
        }
        if (stage.getMode().equalsIgnoreCase("Teams")) {
            if (party.getLeaderTeamLevel() >= stage.getStage() && party.getMemberTeamLevel() >= stage.getStage()) {
                if (party.getLeaderTeamLevel() == stage.getStage()) {
                    System.out.println("Completed level " + stage.getStage() + " for first time");
                    party.updateLeaderTeamsLevel(party.getLeader().getUniqueId());
                } else {
                    System.out.println("That's not the first time (level " + stage.getStage());
                }
                if (party.getMemberTeamLevel() == stage.getStage()) {
                    System.out.println("Completed level " + stage.getStage() + " for first time");
                    party.updateMemberTeamLevel(party.getMember().getUniqueId());
                } else {
                    System.out.println("That's not the first time (level " + stage.getStage());
                }
                party.setCurrentPlayedMode(null);
                party.setCurrentPlayedLevel(-1);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getLeader().getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + party.getMember().getName());
            }
        }
    }
}

