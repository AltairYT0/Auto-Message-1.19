package me.altair.automessage;

import me.altair.automessage.cmds.AutoMessageCommand;
import me.altair.automessage.tags.TagFeature;
import me.altair.automessage.task.MessageTask;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class AutoMessage extends JavaPlugin {

    private static Plugin instance;

    private static MessageTask messageTask;


    @Override
    public void onEnable() {
        instance = this;
        setupConfig();
        getServer().getPluginManager().registerEvents(new TagFeature(), this);
        getCommand("automessage").setExecutor(new AutoMessageCommand());
        messageTask = new MessageTask();
        messageTask.startTask();
    }


    @Override
    public void onDisable() {
        instance = null;
        messageTask.removeTask();
    }

    public static MessageTask AutoMessageTask() {
        return messageTask;
    }
    public static FileConfiguration getCfg() {
        return getInstance().getConfig();
    }
    private void setupConfig() {
        saveDefaultConfig();
    }
    public static Plugin getInstance() {
        return instance;
    }
}
