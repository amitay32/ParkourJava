package me.amitay.Parkour.Utils.ParkourPackage;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.Utils;
import me.amitay.Parkour.Utils.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ParkourManager {
    private Parkour pl;
    private ArrayList<ParkourStage> parkourStages = new ArrayList<>();
    private boolean inParkour;
    private int checkPoint;

    public ParkourManager(Parkour pl) {
        this.pl = pl;
    }


    public ParkourStage getParkourStage(int level, String mode) {
        for (ParkourStage stage : parkourStages) {
            if (stage.getStage() == level && stage.getMode().equalsIgnoreCase(mode)) {
                return stage;
            }
        }
        return null;
    }

    public void loadLocationsSolo() {
        for (ParkourStage stage : parkourStages) {
            if (stage.getMode().equalsIgnoreCase("Solo")) {
                for (int i1 = 0; i1 <= 10; i1++) {
                    for (int i = 0; i <= pl.getConfig().getList("Parkour.Levels.Solo." + i1+ ".CheckPoints").size() - 1; i++)
                        stage.getLocations().add((Location) pl.getConfig().getList("Parkour.Levels.Solo.CheckPoints").get(i));
                }
            }
        }
    }

    public void loadLocationsTeams() {
        for (ParkourStage stage : parkourStages) {
            if (stage.getMode().equalsIgnoreCase("Teams")) {
                for (int i1 = 0; i1<= 10; i1++) {
                    for (int i = 0; i <= pl.getConfig().getList("Parkour.Levels.Teams." + i1+ ".CheckPoints").size() - 1; i++)
                        stage.getLocations().add((Location) pl.getConfig().getList("Parkour.Levels.Teams.CheckPoints").get(i));
                }
            }
        }
    }

    public void startParkourLevel(Player p, ParkourStage parkour) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null) {
            p.sendMessage(Utils.getFormattedText("&cYou need to create a party to play parkour, /party help for more information"));
            return;
        }
        if (parkour.getMode().equalsIgnoreCase("Teams")) {
            if (party.hasMember()) {
                Player member = party.getMember();
                Player leader = party.getLeader();
                member.teleport(parkour.getStartPoint());
                leader.teleport(parkour.getStartPoint());
                party.currentCheckpoint = -1;
                //start function
            }
            return;
        }
        if (parkour.getMode().equalsIgnoreCase("Solo")) {
            Player leader = party.getLeader();
            leader.teleport(parkour.getStartPoint());
            party.currentCheckpoint = -1;
            //start function
        }
    }

    public boolean isInParkour(Player p) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null)
            return false;
        return party.getCurrentPlayedLevel() != -1;
    }

    public int getCheckPoint(Player p) {
        Party party = pl.partyManager.getPlayerParty(p);
        if (party == null)
            return -1;
        return party.getCurrentCheckpoint();
    }

    public void finishLevel(ParkourStage stage, Party party) { //teleport them to spawn
        if (stage.getMode().equalsIgnoreCase("Solo")) {
            if (party.getLeaderSoloLevel() == stage.getStage()) {
                System.out.println("Completed level " + stage.getStage() + " for first time");
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

