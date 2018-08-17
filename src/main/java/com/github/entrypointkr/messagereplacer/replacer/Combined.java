package com.github.entrypointkr.messagereplacer.replacer;

import org.bukkit.Bukkit;

import java.util.Map;
import java.util.logging.Level;

/**
 * Created by JunHyeong on 2018-08-17
 */
public interface Combined {
    Combined addReplacer(Replacer... replacers);

    default Combined addReplacer(Map<?, ?> replacement) {
        try {
            Replacer replacer = Replacers.createReplacer(replacement);
            addReplacer(replacer);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, e, () -> "An error has occured.");
        }
        return this;
    }

    Combined removeReplacer(Replacer... replacers);

    Combined clear();
}
