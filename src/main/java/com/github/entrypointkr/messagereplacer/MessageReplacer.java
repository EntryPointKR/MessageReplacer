package com.github.entrypointkr.messagereplacer;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import com.github.entrypointkr.messagereplacer.handler.LegacyProtocolLibMessageReplacer;
import com.github.entrypointkr.messagereplacer.handler.PlayerChatCacher;
import com.github.entrypointkr.messagereplacer.handler.ProtocolLibMessageReplacer;
import com.github.entrypointkr.messagereplacer.handler.ProtocolSupportHandlerInjector;
import com.github.entrypointkr.messagereplacer.replacer.ChatCacheRemover;
import com.github.entrypointkr.messagereplacer.replacer.ColorizeReplacer;
import com.github.entrypointkr.messagereplacer.replacer.CombinedReplacer;
import com.github.entrypointkr.messagereplacer.replacer.ConfigurableReplacer;
import com.github.entrypointkr.messagereplacer.replacer.DecolorizeReplacer;
import com.github.entrypointkr.messagereplacer.replacer.PredicateReplacer;
import com.github.entrypointkr.messagereplacer.replacer.Scope;
import com.github.entrypointkr.messagereplacer.replacer.ScopeCombined;
import com.github.entrypointkr.messagereplacer.utils.LibUsageChart;
import com.github.entrypointkr.messagereplacer.utils.Metrics;
import com.github.entrypointkr.messagereplacer.utils.Reflections;
import com.github.entrypointkr.messagereplacer.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MessageReplacer extends JavaPlugin {
    public static final CombinedReplacer CHAT_REPLACER = new CombinedReplacer();
    public static final CombinedReplacer PLUGIN_REPLACER = new CombinedReplacer();
    public static final CombinedReplacer DEFAULT_REPLACER = new CombinedReplacer();
    public static final ConfigurableReplacer REPLACER = new ConfigurableReplacer(
            new ScopeCombined()
                    .put(Scope.CHAT, CHAT_REPLACER)
                    .put(Scope.PLUGIN, PLUGIN_REPLACER)
                    .put(Scope.ALL, DEFAULT_REPLACER),
            new CombinedReplacer().addReplacer(
                    new ChatCacheRemover(),
                    DecolorizeReplacer.INSTANCE,
                    new CombinedReplacer()
                            .addReplacer(
                                    new PredicateReplacer(
                                            PlayerChatCacher.IS_PLAYER_CHAT,
                                            CHAT_REPLACER,
                                            PLUGIN_REPLACER
                                    ),
                                    DEFAULT_REPLACER
                            ),
                    ColorizeReplacer.INSTANCE
            )
    );

    @Override
    public void onEnable() {
        saveDefaultConfig();
        REPLACER.load(new File(getDataFolder(), "config.yml"));
        Metrics metrics = new Metrics(this);
        PluginManager manager = Bukkit.getPluginManager();
        if (manager.isPluginEnabled("ProtocolSupport")) {
            ProtocolSupportHandlerInjector.init(this);
            LibUsageChart.addTo(metrics, "ProtocolSupport");
        } else if (manager.isPluginEnabled("ProtocolLib")) {
            ProtocolLibrary.getProtocolManager().addPacketListener(getProperProtocolLibReplacer());
            LibUsageChart.addTo(metrics, "ProtocolLib");
        } else {
            throw new IllegalStateException("Cannot find depend plugins (ProtocolSupport or ProtocolLib)");
        }
        PlayerChatCacher.init(this);
        UpdateChecker.init(this);
    }

    public PacketListener getProperProtocolLibReplacer() {
        if (Reflections.I_CHAT_BASE_COMPONENT != null) {
            return new ProtocolLibMessageReplacer(this);
        } else {
            return new LegacyProtocolLibMessageReplacer(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && "reload".equals(args[0])
                && sender.hasPermission("messagereplacer.reload")) {
            REPLACER.load(new File(getDataFolder(), "config.yml"));
            sender.sendMessage("Reloaded.");
        } else {
            sender.sendMessage("Invalid usage.");
        }
        return true;
    }
}
