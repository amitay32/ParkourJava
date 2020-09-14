package me.amitay.Parkour.Listeners;

import me.amitay.Parkour.Parkour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnJoinListener implements Listener {

    private Parkour main;

    public OnJoinListener(Parkour main) {
        this.main = main;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e){
        playerExist(e.getPlayer());
    }

    public void playerExist(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement statement = main.getMySql().getConnection()
                            .prepareStatement("SELECT * FROM " + main.getMySql().getTable() + " WHERE UUID=?");
                    statement.setString(1, player.getUniqueId().toString());
                    ResultSet results = statement.executeQuery();
                    if (!results.next()) {
                        createPlayer(player);
                        statement.close();
                        results.close();
                    }

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(main);

    }

    private void createPlayer(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    PreparedStatement statement = main.getMySql().getConnection().prepareStatement("INSERT INTO "
                            + main.getMySql().getTable() + " (UUID, SOLO_LEVEL, TEAMS_LEVEL) VALUES (?, ?, ?)");
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setInt(2, 1);
                    statement.setInt(3, 1);
                    statement.executeUpdate();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }.runTaskAsynchronously(main);

    }
}
