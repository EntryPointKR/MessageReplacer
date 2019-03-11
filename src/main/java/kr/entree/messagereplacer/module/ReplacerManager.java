package kr.entree.messagereplacer.module;

import kr.entree.messagereplacer.MessageReplacer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class ReplacerManager implements Listener {
    private final YamlConfiguration config = new YamlConfiguration();
    private final Map<Scope, CombinedReplacer> replacerByScope = new HashMap<>();
    private String lastPlayerMessage = "";

    public void init(File file, MessageReplacer plugin) {
        load(file);
        parse();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void load(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            // Ignore
        }
        try {
            config.loadFromString(builder.toString());
        } catch (InvalidConfigurationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void parse() {
        List<Map<?, ?>> replacements = config.getMapList("replacements");
        if (replacements != null) {
            parse(replacements);
        }
    }

    public void parse(List<Map<?, ?>> replacements) {
        replacerByScope.clear();
        for (Map<?, ?> replacement : replacements) {
            ReplacerType type = Optional.ofNullable(replacement.get("type"))
                    .flatMap(name -> Enums.get(ReplacerType.class, name.toString()))
                    .orElse(ReplacerType.NORMAL);
            Scope scope = Optional.ofNullable(replacement.get("scope"))
                    .flatMap(name -> Enums.get(Scope.class, name.toString()))
                    .orElse(Scope.ALL);
            try {
                Replacer replacer = type.parse(replacement);
                getCombined(scope).add(replacer);
            } catch (Exception ex) {
                MessageReplacer.get().log(ex, "Exceptions when parsing " + replacement);
            }
        }
    }

    public boolean isPlayerChat(String message) {
        return ChatColor.stripColor(message).equals(lastPlayerMessage);
    }

    public CombinedReplacer getCombined(Scope scope) {
        return replacerByScope.computeIfAbsent(scope, s -> CombinedReplacer.of());
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    @SuppressWarnings("deprecation")
    public void onChat(PlayerChatEvent e) {
        lastPlayerMessage = ChatColor.stripColor(String.format(
                e.getFormat(),
                e.getPlayer().getDisplayName(),
                e.getMessage()
        ));
    }
}
