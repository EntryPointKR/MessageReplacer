package com.github.entrypointkr.messagereplacer.utils;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;

import java.nio.charset.StandardCharsets;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class Serializer {
    public static void writeStringBytes(ByteBuf buf, ProtocolVersion version, byte[] bytes) {
        if (isUsingUTF16(version)) {
            buf.writeShort(bytes.length);
            buf.writeBytes(bytes);
        } else {
            VarNumberSerializer.writeVarInt(buf, bytes.length);
            buf.writeBytes(bytes);
        }
    }

    public static byte[] getStringBytes(ProtocolVersion version, String string) {
        if (isUsingUTF16(version)) {
            return string.getBytes(StandardCharsets.UTF_16BE);
        } else {
            if (!isUsingUTF8(version)) {
                throw new IllegalArgumentException();
            }
            return string.getBytes(StandardCharsets.UTF_8);
        }
    }

    public static boolean isUsingUTF16(ProtocolVersion version) {
        return version.getProtocolType() == ProtocolType.PC && version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4);
    }

    public static boolean isUsingUTF8(ProtocolVersion version) {
        return version.getProtocolType() == ProtocolType.PC && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5);
    }

    private Serializer() {
    }
}
