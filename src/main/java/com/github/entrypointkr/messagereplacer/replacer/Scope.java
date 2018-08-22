package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-18
 */
public enum Scope {
    PLUGIN,
    CHAT,
    ALL;

    public static Scope parse(String scopeStr) {
        switch (scopeStr) {
            case "plugin":
                return PLUGIN;
            case "chat":
                return CHAT;
            case "all":
                return ALL;
            default:
                throw new IllegalArgumentException("Unknown scope: " + scopeStr);
        }
    }
}
