package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.handler.PlayerChatCacher;
import com.github.entrypointkr.messagereplacer.utils.CachedSupplier;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Supplier;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class ChatCacheRemover implements Replacer {
    private final Supplier<Plugin> plugin;

    public ChatCacheRemover(Supplier<Plugin> plugin) {
        this.plugin = plugin;
    }

    public ChatCacheRemover() {
        this(new CachedSupplier<>(() ->
                Bukkit.getPluginManager().getPlugin("MessageReplacer")));
    }

    @Override
    public String replace(String content) {
        if (PlayerChatCacher.MESSAGES.contains(content)) {
            Bukkit.getScheduler().runTask(plugin.get(), () -> PlayerChatCacher.MESSAGES.remove(content));
        }
        return content;
    }
}
