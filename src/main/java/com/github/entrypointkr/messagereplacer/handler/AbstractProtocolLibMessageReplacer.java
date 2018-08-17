package com.github.entrypointkr.messagereplacer.handler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.plugin.Plugin;

/**
 * Created by JunHyeong on 2018-08-17
 */
public abstract class AbstractProtocolLibMessageReplacer extends PacketAdapter {
    public AbstractProtocolLibMessageReplacer(Plugin plugin) {
        super(plugin, PacketType.Play.Server.CHAT);
    }
}
