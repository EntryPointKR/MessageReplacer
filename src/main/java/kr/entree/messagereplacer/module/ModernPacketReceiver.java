package kr.entree.messagereplacer.module;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.plugin.Plugin;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class ModernPacketReceiver extends PacketReceiver {
    private final ReplacerManager manager;

    public ModernPacketReceiver(Plugin plugin, ReplacerManager manager) {
        super(plugin, manager, PacketType.Play.Server.CHAT);
        this.manager = manager;
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