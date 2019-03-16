package kr.entree.messagereplacer;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.utility.MinecraftReflection;
import kr.entree.messagereplacer.module.LegacyPacketReceiver;
import kr.entree.messagereplacer.module.Metrics;
import kr.entree.messagereplacer.module.ModernPacketReceiver;
import kr.entree.messagereplacer.module.ReplacerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class MessageReplacer extends JavaPlugin {
    private final ReplacerManager replacerManager = new ReplacerManager();

    public static MessageReplacer get() {
        return (MessageReplacer) Bukkit.getPluginManager().getPlugin("MessageReplacer");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        replacerManager.init(createConfigFile(), this);
        ProtocolLibrary.getProtocolManager().addPacketListener(createProperPacketListener());
        new Metrics(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            replacerManager.load(createConfigFile());
            replacerManager.parse();
            sender.sendMessage("Reloaded!");
        } else {
            sender.sendMessage(ChatColor.RED + "No permission!");
        }
        return true;
    }

    public void log(Throwable throwable, String message) {
        getLogger().log(Level.WARNING, throwable, () -> message);
    }

    public PacketListener createProperPacketListener() {
        try {
            if (MinecraftReflection.getIChatBaseComponentClass() != null) {
                return new ModernPacketReceiver(this, replacerManager);
            }
        } catch (Exception ex) {
            // Ignore
        }
        return new LegacyPacketReceiver(this, replacerManager);
    }

    public ReplacerManager getReplacerManager() {
        return replacerManager;
    }

    private File createConfigFile() {
        return new File(getDataFolder(), "config.yml");
    }
}
