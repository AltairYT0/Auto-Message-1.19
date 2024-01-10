package me.altair.automessage.cmds;

import me.altair.automessage.AutoMessage;
import me.altair.automessage.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoMessageCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("automessage")) {
        if (player.hasPermission("automessage.manage")) {
            if (args.length >= 1) {
                switch (args[0].toLowerCase(Locale.ROOT)) {
                    case "reload":
                        AutoMessage.AutoMessageTask().restartTask();
                        AutoMessage.getInstance().reloadConfig();
                        player.sendMessage(ChatUtil.fix("&b[&6Auto-Message&b] &6->&b Auto-Message reloaded!"));
                        break;
                    case "toggle":
                        if (AutoMessage.getCfg().getBoolean("enabled")) {
                            AutoMessage.getCfg().set("enabled", false);
                            AutoMessage.getInstance().saveConfig();
                            AutoMessage.getInstance().reloadConfig();
                            player.sendMessage(ChatUtil.fix("&b[&6Auto-Message&b] &6->&b Auto-Message is now &cDisabled &r!"));
                        } else {
                            AutoMessage.getCfg().set("enabled", true);
                            AutoMessage.getInstance().saveConfig();
                            AutoMessage.getInstance().reloadConfig();
                            player.sendMessage(ChatUtil.fix("&b[&6Auto-Message&b] &6->&b Auto-Message is now &aEnabled &r!"));
                        }
                        break;
                    case "add":
                        StringBuilder stringBuilder = new StringBuilder();
                        List<String> list2 = new ArrayList<>(AutoMessage.getCfg().getStringList("messages"));
                        for (int i = 1; i < args.length; i++) {
                            stringBuilder.append(args[i]);
                        }
                        list2.add(stringBuilder.toString());
                        AutoMessage.getCfg().set("messages", list2);
                        AutoMessage.getInstance().saveConfig();
                        AutoMessage.getInstance().reloadConfig();
                        player.sendMessage(ChatUtil.fix("&b[&6Auto-Message&b] &6->&b New message added!"));
                        break;
                    case "remove":
                        List<String> list3 = new ArrayList<>(AutoMessage.getCfg().getStringList("messages"));
                        if (isInteger(args[1])) {
                            if (list3.size() >= Integer.parseInt(args[1])) {
                                int index = Integer.parseInt(args[1]);
                                if (list3.size() >= index) {
                                    list3.remove(index);
                                    player.sendMessage(ChatUtil.fix("&b[&6Auto-Message&b] &6->&b Removed Auto-Message number &6" + args[1]));
                                    AutoMessage.getCfg().set("messages", list3);
                                    AutoMessage.getInstance().saveConfig();
                                    AutoMessage.getInstance().reloadConfig();
                                } else {
                                    player.sendMessage(ChatUtil.fix("&b[&6Auto-Message&b] &6->&b There is no message with number &6" + args[1]));
                                }
                            }
                        }
                        break;
                    case "list":
                        StringBuilder stringBuilder1 = new StringBuilder();
                        List<String> list = new ArrayList<>(AutoMessage.getCfg().getStringList("messages"));
                        for (int i = 0; i < list.size(); i++) {
                            String mess = list.get(i);
                            stringBuilder1.append("&r").append(i).append(" - ").append(mess).append("\n");
                        }
                        player.sendMessage(ChatUtil.fix("&b==[&6Auto-Message&b]=="));
                        player.sendMessage(ChatUtil.fix(stringBuilder1.toString()));
                        player.sendMessage(ChatUtil.fix("&b==[&6Auto-Message&b]=="));
                        break;
                    default:
                        player.sendMessage(ChatUtil.fix("&b==[&6Auto-Message&b]=="));
                        player.sendMessage(ChatUtil.fix("&6Auto-Message is ") + String.valueOf(AutoMessage.getCfg().getBoolean("enabled")).replace("true",ChatUtil.fix("&aEnabled")).replace("false",ChatUtil.fix("&cDisabled")));
                        player.sendMessage(ChatUtil.fix("&6/automessage toggle - Toggles the Auto-Message"));
                        player.sendMessage(ChatUtil.fix("&6/automessage reload - Reload config"));
                        player.sendMessage(ChatUtil.fix("&6/automessage add - Adds new messanges to config"));
                        player.sendMessage(ChatUtil.fix("&6/automessage remove - Removes messages from config"));
                        player.sendMessage(ChatUtil.fix("&6/automessage list - Displays list of messages and their numeration"));
                        player.sendMessage(ChatUtil.fix("&b==[&6Auto-Message&b]=="));
                        break;
                    }
                } else {
                player.sendMessage(ChatUtil.fix("&b==[&6Auto-Message&b]=="));
                player.sendMessage(ChatUtil.fix("&6Auto-Message is ") + String.valueOf(AutoMessage.getCfg().getBoolean("enabled")).replace("true",ChatUtil.fix("&aEnabled")).replace("false",ChatUtil.fix("&cDisabled")));
                player.sendMessage(ChatUtil.fix("&6/automessage toggle - Toggles the Auto-Message"));
                player.sendMessage(ChatUtil.fix("&6/automessage reload - Reload config"));
                player.sendMessage(ChatUtil.fix("&6/automessage add - Adds new messanges to config"));
                player.sendMessage(ChatUtil.fix("&6/automessage remove - Removes messages from config"));
                player.sendMessage(ChatUtil.fix("&6/automessage list - Displays list of messages and their numeration"));
                player.sendMessage(ChatUtil.fix("&b==[&6Auto-Message&b]=="));
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        return null;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
