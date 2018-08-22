package com.github.entrypointkr.messagereplacer.replacer;

import org.bukkit.ChatColor;

/**
 * Created by JunHyeong on 2018-08-22
 */
public class DecolorizeReplacer implements Replacer {
    public static final DecolorizeReplacer INSTANCE = new DecolorizeReplacer();

    private DecolorizeReplacer() {
    }

    @Override
    public String replace(String content) {
        return ChatColor.stripColor(content);
    }
}
