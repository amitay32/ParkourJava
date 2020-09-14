package me.amitay.Parkour.Utils;

import me.amitay.Parkour.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySql {
    private Parkour main;
    private Connection connection;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String table;

    public MySql(Parkour instance) {
        main = instance;
        host = main.getConfig().getString("host");
        database = main.getConfig().getString("database");
        username = main.getConfig().getString("username");
        password = main.getConfig().getString("password");
        port = main.getConfig().getInt("port");
        table = "player_data2";
        try {

            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
                        password);
                PreparedStatement statement = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS `player_data2` (uuid char(36), solo_level integer, teams_level integer)");
                statement.executeUpdate();
                statement.close();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySql has been connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getTable() {
        return table;
    }

}