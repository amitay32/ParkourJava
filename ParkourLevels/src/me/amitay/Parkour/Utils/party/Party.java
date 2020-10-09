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

    public void endParkour(ParkourStage stage) {
        stage.freeWorld(leader.getLocation().getWorld());
        leader.sendMessage("&eYou have finished the parkour!");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + leader.getName());
        stage.executeCommands(leader.getName());
        if (member != null) {
            member.sendMessage("&eYou have finished the parkour!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + member.getName());
            stage.executeCommands(member.getName());
            handlePlayerWin(member);
        }
        handlePlayerWin(leader);
        clear();
    }

    private void handlePlayerWin(Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = pl.getMySql().getConnection()
                            .prepareStatement("SELECT * FROM " + pl.getMySql().getTable() + " WHERE UUID=?");
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet results = statement.executeQuery();
                    results.next();
                    int level = results.getInt(currentPlayedMode.toUpperCase() + "_LEVEL");
                    statement.close();
                    results.close();
                    updatePlayerStats(player, level);
                } catch (SQLException e) {
                    leaderTeamLevel = 1;
                    memberTeamLevel = 1;
                    e.printStackTrace();
                }
            }

        }.runTaskAsynchronously(pl);
    }
    public void updatePlayerStats(Player player, int level){
        if (level == currentPlayedLevel - 1){
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        PreparedStatement statement = pl.getMySql().getConnection()
                                .prepareStatement("UPDATE " + pl.getMySql().getTable() + " SET " + currentPlayedMode.toUpperCase() + "_LEVEL=? WHERE UUID=?");
                        statement.setInt(1, currentPlayedLevel);
                        statement.setString(2, player.getUniqueId().toString());
                        statement.executeUpdate();
                        statement.close();
                        if (leader.equals(player))
                            loadPlayerStats(player, 'l');
                        else if (member != null && member.equals(player))
                            loadPlayerStats(member, 'm');
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }.runTaskAsynchronously(pl);
        }
    }
    public void startParkour(int level, String mode) {
        currentPlayedLevel = level;
        currentPlayedMode = mode;
    }

    public int getCurrentCheckPointLevel() {
        return currentCheckPointLevel;
    }

    public void setCheckPoint(int level, Location loc, Block block) {
        currentCheckPointLevel = level;
        currentCheckpoint = loc;
        steppedCheckPoints.add(block);
    }

    public Location getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    public boolean hasSteppedOn(Block block) {
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
        clear();
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

    public int getCurrentPlayedLevel() {
        return currentPlayedLevel;
    }

    public void setCurrentPlayedLevel(int level) {
        currentPlayedLevel = level;
    }

    public String getCurrentPlayedMode() {
        return currentPlayedMode;
    }

    public void setCurrentPlayedMode(String mode) {
        currentPlayedMode = mode;
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
    private void clear(){
        steppedCheckPoints.clear();
        currentCheckPointLevel = 0;
        currentCheckpoint = null;
        currentPlayedLevel = -1;
        currentPlayedMode = null;
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
                    } else if (c == 'm') {
                        memberTeamLevel = teamlevel;
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

}
