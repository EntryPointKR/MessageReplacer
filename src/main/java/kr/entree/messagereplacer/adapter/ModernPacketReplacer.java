package kr.entree.messagereplacer.adapter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import kr.entree.messagereplacer.replacer.ReplacerManager;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class ModernPacketReplacer extends PacketMessageReplacer {
    public ModernPacketReplacer(Plugin plugin, ReplacerManager manager) {
        super(plugin, manager, PacketType.Play.Server.CHAT);
    }

    @Override
    protected Optional<String> getMessage(PacketContainer packet) {
        return Optional.ofNullable(packet.getChatComponents().read(0))
                .map(WrappedChatComponent::getJson)
                .map(ComponentSerializer::parse)
                .map(components -> components[0].toLegacyText());
    }

    @Override
    protected void writeMessage(PacketContainer packet, String message) {
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
    }
}
