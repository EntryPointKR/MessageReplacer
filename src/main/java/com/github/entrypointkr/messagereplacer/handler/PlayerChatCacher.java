package com.github.entrypointkr.messagereplacer.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class PlayerChatCacher implements Listener {
    public static final Set<String> MESSAGES = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public static final Predicate<String> IS_PLAYER_CHAT = MESSAGES::contains;

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new PlayerChatCacher(), plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        String formatedMessage = ChatColor.RESET + String.format(e.getFormat(), e.getPlayer().getDisplayName(), message);
        MESSAGES.add(ChatColor.stripColor(formatedMessage));
    }
}
