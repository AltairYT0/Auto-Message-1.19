package me.altair.automessage.task;

import me.altair.automessage.AutoMessage;
import me.altair.automessage.util.ChatUtil;
import me.altair.automessage.util.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class MessageTask {

    private int taskId;

    private static List<String> messages = new ArrayList<>();

    public void startTask() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(AutoMessage.getInstance(), new Runnable() {
            int time = 0;
            @Override
            public void run() {
                int cooldown = AutoMessage.getCfg().getInt("cooldown");
                if (AutoMessage.getCfg().getBoolean("enabled")) {
                    boolean addSound = AutoMessage.getCfg().getBoolean("Sound-on-message");
                    if (messages.isEmpty()) {
                        getMessages();
                    }
                    if (time == 0) {
                        getMessages();
                    }
                    if (time == cooldown) {
                        if (Bukkit.getServer().getOnlinePlayers().size() > 0) {
                            if (addSound) {
                                SoundUtil.playSoundForEveryone(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                            }
                            Bukkit.getServer().broadcastMessage(ChatUtil.fix(messages.get(0)));
                            messages.remove(0);
                            time = 0;
                        } else {
                            time = 0;
                        }
                    } else {
                        time++;
                    }
                }
            }
        }, 0L, 20L);
    }

    public void restartTask() {
        Bukkit.getScheduler().cancelTask(taskId);
        messages.clear();
        AutoMessage.getInstance().reloadConfig();
        startTask();
    }

    public void removeTask() {
        Bukkit.getScheduler().cancelTask(taskId);
        messages.clear();
        AutoMessage.getInstance().reloadConfig();
    }
    public void getMessages() {
        AutoMessage.getInstance().reloadConfig();
        List<String> msg = AutoMessage.getCfg().getStringList("messages");
        if (!msg.isEmpty()) {
            messages.addAll(msg);
        } else {
            messages.add("No messages found in config.yml");
        }
    }
    public static void restartMessages() {
        messages.clear();
    }
}
