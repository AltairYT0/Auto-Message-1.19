package me.altair.automessage.util;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String fix(String message) {
       return ChatColor.translateAlternateColorCodes('&', message);
    }
}
