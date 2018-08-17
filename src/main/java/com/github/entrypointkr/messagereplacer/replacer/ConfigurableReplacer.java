package com.github.entrypointkr.messagereplacer.replacer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class ConfigurableReplacer implements IConfigurableReplacer {
    private final FileConfiguration config = new YamlConfiguration();
    public final Combined combined;
    public final Replacer replacer;

    /*
        Object: Chain(Chain(CacheRemover, CombinedReplacer), ColorizeReplacer)
        Flow: CacheRemover -> CombinedReplacer -> ColorizeReplacer
     */
    public static ConfigurableReplacer createDefault(Supplier<Plugin> plugin) {
        CombinedReplacer combinedReplacer = new CombinedReplacer();
        ChainReplacer chainReplacer = new ChainReplacer(new ChatCacheRemover(plugin), combinedReplacer);
        return ofColorizable(combinedReplacer, chainReplacer);
    }

    public static ConfigurableReplacer createDefault() {
        return createDefault(() -> Bukkit.getPluginManager().getPlugin("MessageReplacer"));
    }

    public static ConfigurableReplacer ofColorizable(Combined combined, Replacer replacer) {
        ChainReplacer chainReplacer = new ChainReplacer(replacer, ColorizeReplacer.INSTANCE);
        return new ConfigurableReplacer(combined, chainReplacer);
    }

    public static ConfigurableReplacer ofColorizable(ICombinedReplacer combinedReplacer) {
        return ofColorizable(combinedReplacer, combinedReplacer);
    }

    public ConfigurableReplacer(Combined combined, Replacer replacer) {
        this.combined = combined;
        this.replacer = replacer;
    }

    public ConfigurableReplacer(ICombinedReplacer combinedReplacer) {
        this(combinedReplacer, combinedReplacer);
    }

    @Override
    public String replace(String content) {
        return replacer.replace(content);
    }

    @Override
    public void load(String yamlContents) throws InvalidConfigurationException {
        config.loadFromString(yamlContents);
    }

    @Override
    public void update() {
        combined.clear();
        for (Map<?, ?> replacement : config.getMapList("replacements")) {
            combined.addReplacer(replacement);
        }
    }
}
