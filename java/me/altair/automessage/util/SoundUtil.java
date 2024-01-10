package me.altair.automessage.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void playSoundForEveryone(Sound sound) {
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            players.playSound(players.getLocation(),sound,1,1);
        }
    }
}
