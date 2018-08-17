package com.github.entrypointkr.messagereplacer.handler;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.entrypointkr.messagereplacer.MessageReplacer;
import org.bukkit.plugin.Plugin;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class LegacyProtocolLibMessageReplacer extends AbstractProtocolLibMessageReplacer {
    public LegacyProtocolLibMessageReplacer(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        String message = packet.getStrings().read(0);
        String processed = MessageReplacer.REPLACER.replace(message);
        if (!message.equals(processed)) {
            packet.getStrings().write(0, processed);
        }
    }
}
