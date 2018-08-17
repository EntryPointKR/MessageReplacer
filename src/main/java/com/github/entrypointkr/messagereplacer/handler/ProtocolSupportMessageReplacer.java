package com.github.entrypointkr.messagereplacer.handler;

import com.github.entrypointkr.messagereplacer.MessageReplacer;
import com.github.entrypointkr.messagereplacer.utils.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.zplatform.ServerPlatform;

/**
 * Created by JunHyeong on 2018-08-16
 */
public class ProtocolSupportMessageReplacer extends Connection.PacketListener {
    private final Connection connection;

    public ProtocolSupportMessageReplacer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onRawPacketSending(RawPacketEvent event) {
        ByteBuf buf = event.getData();
        int id = VarNumberSerializer.readVarInt(buf);
        if (id == ServerPlatform.get().getPacketFactory().getOutPlayChatPacketId()) {
            ProtocolVersion version = connection.getVersion();
            String json = StringSerializer.readString(buf, version);
            byte type = buf.readByte();

            BaseComponent component = ChatAPI.fromJSON(json);
            String text = component.toLegacyText();
            String converted = MessageReplacer.REPLACER.replace(text);
            if (!text.equals(converted)) {
                String newJson = ChatAPI.toJSON(new TextComponent(converted));
                byte[] jsonBytes = Serializer.getStringBytes(version, newJson);

                ByteBuf newBuf = Unpooled.buffer(3 + jsonBytes.length); // ID(1) +  JSON STRING & LEN + TYPE(1)
                VarNumberSerializer.writeVarInt(newBuf, id);
                Serializer.writeStringBytes(newBuf, version, jsonBytes);
                newBuf.writeByte(type);
                event.setData(newBuf);
            }
        }
    }
}
