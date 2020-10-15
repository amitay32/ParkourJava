package me.amitay.parkour.utils.party;

import me.amitay.parkour.Parkour;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Party {

    public Player leader;
    private Player member;
    public boolean ChatEnabledPlayer = false;
    public boolean ChatEnabledLeader = false;
    private int leaderTeamLevel;
    private int leaderSoloLevel;
    private int memberTeamLevel;
    private int memberSoloLevel;

    private Parkour pl;


    public Party(Player leader, Parkour pl) {
        this.pl = pl;
        this.leader = leader;
        loadPlayerStats(leader, 'l');
        memberTeamLevel = -1;
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
        ChatEnabledPlayer = !ChatEnabledPlayer;
    }

    public void setPartyChatLeader() {
        ChatEnabledLeader = !ChatEnabledLeader;
    }

    public boolean partyChatEnabledLeader() {
        return ChatEnabledLeader;
    }

    public boolean partyChatEnabledMemebr() {
        return ChatEnabledPlayer;
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
