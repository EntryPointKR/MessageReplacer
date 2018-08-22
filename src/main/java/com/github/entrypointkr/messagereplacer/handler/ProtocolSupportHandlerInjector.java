package com.github.entrypointkr.messagereplacer.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import protocolsupport.api.events.ConnectionOpenEvent;

/**
 * Created by JunHyeong on 2018-08-16
 */
public class ProtocolSupportHandlerInjector implements Listener {
    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ProtocolSupportHandlerInjector(), plugin);
    }

    @EventHandler
    public void onConnectionOpen(ConnectionOpenEvent e) {
        e.getConnection().addPacketListener(new ProtocolSupportMessageReplacer(e.getConnection()));
    }
}
