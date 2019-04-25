package kr.entree.messagereplacer.adapter;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketContainer;
import kr.entree.messagereplacer.replacer.ReplacerManager;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
@SuppressWarnings("deprecation")
public class LegacyPacketReplacer extends PacketMessageReplacer {
    public LegacyPacketReplacer(Plugin plugin, ReplacerManager manager) {
        super(plugin, ConnectionSide.SERVER_SIDE, manager, Packets.Server.CHAT);
    }

    @Override
    protected Optional<String> getMessage(PacketContainer packet) {
        return Optional.ofNullable(packet.getStrings().read(0));
    }

    @Override
    protected void writeMessage(PacketContainer packet, String message) {
        packet.getStrings().write(0, message);
    }
}
