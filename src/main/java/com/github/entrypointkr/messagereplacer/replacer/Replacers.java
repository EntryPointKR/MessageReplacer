package com.github.entrypointkr.messagereplacer.replacer;

import java.util.Map;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class Replacers {
    public static Replacer createReplacer(Map<?, ?> objectMap) throws Exception {
        String typeStr = objectMap.containsKey("type")
                ? objectMap.get("type").toString()
                : "default";
        String from = objectMap.get("from").toString();
        String to = objectMap.get("to").toString();
        return createReplacer(typeStr, from, to);
    }

    public static Replacer createReplacer(String typeStr, Object... args) throws Exception {
        return ReplacerType.getType(typeStr)
                .createReplacer(args);
    }

    private Replacers() {
    }
}
