package com.github.entrypointkr.messagereplacer.handler;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.github.entrypointkr.messagereplacer.MessageReplacer;
import org.bukkit.plugin.Plugin;

/**
 * Created by JunHyeong on 2018-08-16
 */
public class ProtocolLibMessageReplacer extends AbstractProtocolLibMessageReplacer {
    public ProtocolLibMessageReplacer(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        WrappedChatComponent chat = packet.getChatComponents().read(0);
        Object modifiedComponent = MessageReplacer.REPLACER.replaceChatComponent(chat.getHandle());
        if (chat.getHandle() != modifiedComponent) {
            packet.getChatComponents().write(0, WrappedChatComponent.fromHandle(modifiedComponent));
        }
    }
}
