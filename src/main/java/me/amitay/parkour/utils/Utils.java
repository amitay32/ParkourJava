package me.amitay.parkour.utils;

import org.bukkit.ChatColor;

public class Utils {
    public static String getFormattedText(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
