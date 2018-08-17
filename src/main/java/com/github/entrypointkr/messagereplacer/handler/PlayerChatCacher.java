package com.github.entrypointkr.messagereplacer.handler;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class PlayerChatCacher implements Listener {
    public static final Set<String> MESSAGES = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        String formatedMessage = ChatColor.RESET + String.format(e.getFormat(), e.getPlayer().getDisplayName(), message);
        MESSAGES.add(ChatColor.stripColor(formatedMessage));
    }
}
