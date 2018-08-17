package com.github.entrypointkr.messagereplacer.replacer;

import org.bukkit.ChatColor;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class ColorizeReplacer implements Replacer {
    public static String colorize(String contents) {
        return ChatColor.translateAlternateColorCodes('&', contents);
    }

    @Override
    public String replace(String content) {
        return colorize(content);
    }
}
