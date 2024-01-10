package me.altair.automessage.tags;

import me.altair.automessage.AutoMessage;
import me.altair.automessage.util.ChatUtil;
import me.altair.automessage.util.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Map;

public class TagFeature implements Listener {

    @EventHandler
    public void onTag(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (message.contains("#")) {
            List<Map<?, ?>> tags = AutoMessage.getCfg().getMapList("tags");
            for (Map<?, ?> tagMap : tags) {
                String tag = (String) tagMap.get("tag");
                String formattedTag = "#" + tag;
                List<String> tagContents = (List<String>) tagMap.get("messages");
                if (message.contains(formattedTag)) {
                    SoundUtil.playSoundForEveryone(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);

                    Bukkit.getScheduler().runTaskLater(AutoMessage.getInstance(), () -> {
                        tagContents.stream().map(ChatUtil::fix).forEach(broadcastedContent -> Bukkit.getServer().broadcastMessage(broadcastedContent));
                    }, 1L);
                }
            }
        }
    }
}
