package me.amitay.Parkour.Utils.party;

import me.amitay.Parkour.Parkour;
import me.amitay.Parkour.Utils.ParkourPackage.ParkourStage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    public Player leader;
    private Player member;
    public boolean chatEnabledPlayer = false;
    public boolean chatEnabledLeader = false;
    private int leaderTeamLevel;
    private int leaderSoloLevel;
    private int memberTeamLevel;
    private int memberSoloLevel;
    public int currentPlayedLevel;
    public String currentPlayedMode;
    public Location currentCheckpoint;
    private Parkour pl;
    private int currentCheckPointLevel;
    private List<Block> steppedCheckPoints = new ArrayList<>();

    public Party(Player leader, Parkour pl) {
        this.pl = pl;
        this.leader = leader;
        loadPlayerStats(leader, 'l');
        memberTeamLevel = -1;
        currentPlayedLevel = -1;
    }
    public void endParkour(ParkourStage stage){
        leader.sendMessage("&eYou have finished the parkour! Good job!");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + leader.getName());
        if (getMember() != null) {
            member.sendMessage("&eYou have finished the parkour! Good job!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + member.getName());
        }

        //handle reward
    }
    public void startParkour(int level, String mode){
        currentPlayedLevel = level;
        currentPlayedMode = mode;
    }
    public int getCurrentCheckPointLevel(){
        return currentCheckPointLevel;
    }
    public void setCheckPoint(int level, Location loc, Block block){
        currentCheckPointLevel = level;
        currentCheckpoint = loc;
        steppedCheckPoints.add(block);
    }
    public Location getCurrentCheckpoint() {
        return currentCheckpoint;
    }
    public boolean hasSteppedOn(Block block){
        return steppedCheckPoints.contains(block);
    }

    public boolean containsPlayer(Player player) {
        return (leader != null && leader.equals(player)) || (member != null && member.equals(player));
    }

    public boolean hasMember() {
        return member != null;
    }

    public String getMemberName() {
        return member.getName();
    }

    public String getLeaderName() {
        return leader.getName();
    }

    public void deleteParty() {
        leader = null;
        member = null;
    }

    public void addmember(Player player) {
        loadPlayerStats(player, 'm');
        member = player;
    }

    public void setmember(Player player) {
        loadPlayerStats(player, 'm');
        member = player;
    }

    public Player getMember() {
        return member;
    }

    public Player getLeader() {
        return leader;
    }

    public void setPartyChatPlayer() {
        chatEnabledPlayer = !chatEnabledPlayer;
    }

    public void setPartyChatLeader() {
        chatEnabledLeader = !chatEnabledLeader;
    }

    public boolean partyChatEnabledLeader() {
        return chatEnabledLeader;
    }

    public boolean partyChatEnabledMemebr() {
        return chatEnabledPlayer;
    }

    public int getCurrentPlayedLevel(){
        return currentPlayedLevel;
    }
    public void setCurrentPlayedLevel(int level){
        currentPlayedLevel = level;
    }
    public String getCurrentPlayedMode(){
        return currentPlayedMode;
    }
    public void setCurrentPlayedMode(String mode){
        currentPlayedMode = mode;
    }

    public void loadPlayerStats(Player player, char c) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = pl.getMySql().getConnection()
                            .prepareStatement("SELECT * FROM " + pl.getMySql().getTable() + " WHERE UUID=?");
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet results = statement.executeQuery();
                    results.next();
                    int teamlevel = results.getInt("TEAMS_LEVEL");
                    int sololevel = results.getInt("SOLO_LEVEL");
                    if (c == 'l') {
                        leaderTeamLevel = teamlevel;
                        leaderSoloLevel = sololevel;
                    }
                    else if (c == 'm') {
                        memberTeamLevel = teamlevel;
                        memberSoloLevel = sololevel;
                    }
                    statement.close();
                    results.close();

                } catch (SQLException e) {
                    leaderTeamLevel = 1;
                    memberTeamLevel = 1;
                    e.printStackTrace();
                }
            }

        }.runTaskAsynchronously(pl);
    }
    public int getLeaderTeamLevel() {
        return leaderTeamLevel;
    }
    public int getMemberTeamLevel() {
        return memberTeamLevel;
    }
    public int getLeaderSoloLevel() {
        return leaderSoloLevel;
    }
    public int getMemberSoloLevel() {
        return memberSoloLevel;
    }

    public void updateLeaderSoloLevel(UUID uuid) {
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    PreparedStatement statement = pl.getMySql().getConnection()
                            .prepareStatement("UPDATE " + pl.getMySql().getTable() + " SET SOLO_LEVEL=? WHERE UUID=?");
                    statement.setInt(1, leaderSoloLevel + 1);
                    statement.setString(2, uuid.toString());
                    statement.executeUpdate();
                    statement.close();
                    leaderSoloLevel++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.runTaskAsynchronously(pl);

    }
    public void updateLeaderTeamsLevel(UUID uuid) {
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    PreparedStatement statement = pl.getMySql().getConnection()
                            .prepareStatement("UPDATE " + pl.getMySql().getTable() + " SET TEAMS_LEVEL=? WHERE UUID=?");
                    statement.setInt(1, leaderTeamLevel + 1);
                    statement.setString(2, uuid.toString());
                    statement.executeUpdate();
                    statement.close();
                    leaderTeamLevel++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.runTaskAsynchronously(pl);

    }
    public void updateMemberSoloLevel(UUID uuid) {
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    PreparedStatement statement = pl.getMySql().getConnection()
                            .prepareStatement("UPDATE " + pl.getMySql().getTable() + " SET SOLO_LEVEL=? WHERE UUID=?");
                    statement.setInt(1, memberSoloLevel + 1);
                    statement.setString(2, uuid.toString());
                    statement.executeUpdate();
                    statement.close();
                    memberSoloLevel++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.runTaskAsynchronously(pl);

    }
    public void updateMemberTeamLevel(UUID uuid) {
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    PreparedStatement statement = pl.getMySql().getConnection()
                            .prepareStatement("UPDATE " + pl.getMySql().getTable() + " SET TEAMS_LEVEL=? WHERE UUID=?");
                    statement.setInt(1, memberTeamLevel + 1);
                    statement.setString(2, uuid.toString());
                    statement.executeUpdate();
                    statement.close();
                    memberTeamLevel++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.runTaskAsynchronously(pl);

    }

}
