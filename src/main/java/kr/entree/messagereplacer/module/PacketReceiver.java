package kr.entree.messagereplacer.module;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import kr.entree.messagereplacer.MessageReplacer;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public abstract class PacketReceiver extends PacketAdapter {
    private final ReplacerManager manager;

    public PacketReceiver(AdapterParameteters params, ReplacerManager manager) {
        super(params);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ReplacerManager manager, PacketType... types) {
        super(plugin, types);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, Iterable<? extends PacketType> types, ReplacerManager manager) {
        super(plugin, types);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ListenerPriority listenerPriority, Iterable<? extends PacketType> types, ReplacerManager manager) {
        super(plugin, listenerPriority, types);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ListenerPriority listenerPriority, Iterable<? extends PacketType> types, ReplacerManager manager, ListenerOptions... options) {
        super(plugin, listenerPriority, types, options);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ListenerPriority listenerPriority, ReplacerManager manager, PacketType... types) {
        super(plugin, listenerPriority, types);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ReplacerManager manager, Integer... packets) {
        super(plugin, connectionSide, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ListenerPriority listenerPriority, Set<Integer> packets, ReplacerManager manager) {
        super(plugin, connectionSide, listenerPriority, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, GamePhase gamePhase, Set<Integer> packets, ReplacerManager manager) {
        super(plugin, connectionSide, gamePhase, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ListenerPriority listenerPriority, GamePhase gamePhase, Set<Integer> packets, ReplacerManager manager) {
        super(plugin, connectionSide, listenerPriority, gamePhase, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ListenerPriority listenerPriority, ReplacerManager manager, Integer... packets) {
        super(plugin, connectionSide, listenerPriority, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ListenerOptions[] options, ReplacerManager manager, Integer... packets) {
        super(plugin, connectionSide, options, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, GamePhase gamePhase, ReplacerManager manager, Integer... packets) {
        super(plugin, connectionSide, gamePhase, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ListenerPriority listenerPriority, GamePhase gamePhase, ReplacerManager manager, Integer... packets) {
        super(plugin, connectionSide, listenerPriority, gamePhase, packets);
        this.manager = manager;
    }

    public PacketReceiver(Plugin plugin, ConnectionSide connectionSide, ListenerPriority listenerPriority, GamePhase gamePhase, ListenerOptions[] options, ReplacerManager manager, Integer... packets) {
        super(plugin, connectionSide, listenerPriority, gamePhase, options, packets);
        this.manager = manager;
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    protected abstract String getMessage(PacketContainer packet);

    protected abstract void writeMessage(PacketContainer packet, String message);

    @Override
    public final void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        String before = getMessage(packet);
        Optional<String> afterOpt = replace(before);
        if (afterOpt.isPresent()) {
            writeMessage(packet, afterOpt.get());
        } else {
            event.setCancelled(true);
        }
    }

    protected Optional<String> replace(String text) {
        try {
            Replacer all = manager.getCombined(Scope.ALL);
            Replacer proper = manager.getCombined(manager.isPlayerChat(text) ? Scope.CHAT : Scope.PLUGIN);
            String replaced = all.replace(proper.replace(text));
            return Optional.of(colorize(replaced));
        } catch (ReplacerCancelException ex) {
            return Optional.empty();
        } catch (ReplacerException ex) {
            MessageReplacer.get().log(ex, "Exceptions from " + ex.getReplacer().getClass().getSimpleName());
        }
        return Optional.of(text);
    }
}
