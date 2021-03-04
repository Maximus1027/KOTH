package me.maximus1027.KOTH.Utils;

import org.bukkit.ChatColor;

public class ColorCoding {
    public static String changeColor(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
