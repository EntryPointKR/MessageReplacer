package com.github.entrypointkr.messagereplacer;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import com.github.entrypointkr.messagereplacer.handler.LegacyProtocolLibMessageReplacer;
import com.github.entrypointkr.messagereplacer.handler.ProtocolLibMessageReplacer;
import com.github.entrypointkr.messagereplacer.handler.ProtocolSupportHandlerInjector;
import com.github.entrypointkr.messagereplacer.replacer.ConfigurableReplacer;
import com.github.entrypointkr.messagereplacer.utils.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MessageReplacer extends JavaPlugin {
    public static final ConfigurableReplacer REPLACER = ConfigurableReplacer.ofColorizable();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        REPLACER.load(new File(getDataFolder(), "config.yml"));
        PluginManager manager = Bukkit.getPluginManager();
        if (manager.isPluginEnabled("ProtocolSupport")) {
            Bukkit.getPluginManager().registerEvents(new ProtocolSupportHandlerInjector(), this);
        } else if (manager.isPluginEnabled("ProtocolLib")) {
            ProtocolLibrary.getProtocolManager().addPacketListener(getProperProtocolLibReplacer());
        } else {
            throw new IllegalStateException("Cannot find depend plugins (ProtocolSupport or ProtocolLib)");
        }
    }

    private PacketListener getProperProtocolLibReplacer() {
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
