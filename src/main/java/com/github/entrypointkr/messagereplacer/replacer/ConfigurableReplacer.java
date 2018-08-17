package com.github.entrypointkr.messagereplacer.replacer;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Map;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class ConfigurableReplacer implements IConfigurableReplacer {
    private final FileConfiguration config = new YamlConfiguration();
    public final Combined combined;
    public final Replacer replacer;

    public static ConfigurableReplacer ofColorizable() {
        CombinedReplacer combinedReplacer = new CombinedReplacer();
        CombinedReplacer colorizeReplacer = new CombinedReplacer()
                .addReplacer(new ColorizeReplacer(), combinedReplacer);
        return new ConfigurableReplacer(combinedReplacer, colorizeReplacer);
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
