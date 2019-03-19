package kr.entree.messagereplacer.adapter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import kr.entree.messagereplacer.replacer.ReplacerManager;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.plugin.Plugin;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class ModernPacketReplacer extends PacketMessageReplacer {
    public ModernPacketReplacer(Plugin plugin, ReplacerManager manager) {
        super(plugin, manager, PacketType.Play.Server.CHAT);
    }

    @Override
    protected String getMessage(PacketContainer packet) {
        String json = packet.getChatComponents().read(0).getJson();
        return ComponentSerializer.parse(json)[0].toLegacyText();
    }

    @Override
    protected void writeMessage(PacketContainer packet, String message) {
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
    }
}
