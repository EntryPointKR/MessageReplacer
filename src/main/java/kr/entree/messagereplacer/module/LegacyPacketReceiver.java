package kr.entree.messagereplacer.module;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.plugin.Plugin;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
@SuppressWarnings("deprecation")
public class LegacyPacketReceiver extends PacketReceiver {
    public LegacyPacketReceiver(Plugin plugin, ReplacerManager manager) {
        super(plugin, ConnectionSide.SERVER_SIDE, manager, Packets.Server.CHAT);
    }

    @Override
    protected String getMessage(PacketContainer packet) {
        return packet.getStrings().read(0);
    }

    @Override
    protected void writeMessage(PacketContainer packet, String message) {
        packet.getStrings().write(0, message);
    }
}
