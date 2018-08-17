package com.github.entrypointkr.messagereplacer.handler;

import com.github.entrypointkr.messagereplacer.MessageReplacer;
import com.github.entrypointkr.messagereplacer.Reflections;
import protocolsupport.api.Connection;

import java.lang.reflect.Field;

/**
 * Created by JunHyeong on 2018-08-16
 */
public class ProtocolSupportMessageReplacer extends Connection.PacketListener {
    @Override
    public void onPacketSending(PacketEvent event) {
        Object packet = event.getPacket();
        if (packet.getClass() == Reflections.PACKET_OUT_CHAT) {
            process(packet);
        }
    }

    private void process(Object packet) {
        try {
            Field componentField = Reflections.findDeclaredField(packet.getClass(), Reflections.I_CHAT_BASE_COMPONENT, 0);
            Object component = componentField.get(packet);
            Object processed = MessageReplacer.REPLACER.replaceChatComponent(component);
            if (component != processed) {
                componentField.set(packet, processed);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //    @Override
//    public void onRawPacketSending(RawPacketEvent event) {
//        ByteBuf buf = event.getData();
//        int id = VarNumberSerializer.readVarInt(buf);
//        if (id == ServerPlatform.get().getPacketFactory().getOutPlayChatPacketId()) {
//            ProtocolVersion version = connection.getVersion();
//            String json = StringSerializer.readString(buf, version);
//            String converted = plugin.replaceJson(json);
//            if (!json.equals(converted)) {
//                ByteBuf newBuf = Unpooled.buffer(2 + converted.length()); // ID(1) + JSON STRING + TYPE(1)
//                VarNumberSerializer.writeVarInt(newBuf, id);
//                StringSerializer.writeString(buf, version, converted);
//                event.setData(newBuf);
//            }
//        }
}
