package com.github.entrypointkr.messagereplacer.replacer;

import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by JunHyeong on 2018-08-17
 */
public interface Configurable {
    void load(String yamlContents) throws InvalidConfigurationException;

    default void load(File configFile) {
        try {
            StringBuilder builder = new StringBuilder();
            Files.lines(Paths.get(configFile.toURI()), Charset.forName("UTF-8"))
                    .forEach(line -> builder.append(line).append('\n'));
            load(builder.toString());
            update();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void update();
}
