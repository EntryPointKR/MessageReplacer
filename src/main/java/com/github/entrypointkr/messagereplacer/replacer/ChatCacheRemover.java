package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.handler.PlayerChatCacher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class ChatCacheRemover implements Replacer {
    private final Supplier<Plugin> plugin;

    public ChatCacheRemover(Supplier<Plugin> plugin) {
        this.plugin = plugin;
    }

    @Override
    public String replace(String content) {
        String stripColors = ChatColor.stripColor(content);
        if (PlayerChatCacher.MESSAGES.contains(stripColors)) {
            Bukkit.getScheduler().runTask(plugin.get(), () -> PlayerChatCacher.MESSAGES.remove(stripColors));
        }
        return content;
    }
}
