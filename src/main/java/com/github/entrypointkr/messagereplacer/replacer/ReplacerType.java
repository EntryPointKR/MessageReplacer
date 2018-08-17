package com.github.entrypointkr.messagereplacer.replacer;

import org.apache.commons.lang.Validate;

/**
 * Created by JunHyeong on 2018-08-17
 */
public enum ReplacerType {
    DEFAULT("default", args -> {
        Validate.isTrue(args.length >= 2);
        return new DefaultReplacer(args[0].toString(), args[1].toString());
    }),
    REGEX("regex", args -> {
        Validate.isTrue(args.length >= 2);
        return new RegexReplacer(args[0].toString(), args[1].toString());
    });

    public static ReplacerType getType(String name) throws Exception {
        for (ReplacerType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new Exception(String.format("The %s is invalid type.", name));
    }

    private final String name;
    private final ReplacerFactory factory;

    ReplacerType(String name, ReplacerFactory factory) {
        this.name = name;
        this.factory = factory;
    }

    public Replacer createReplacer(Object... args) {
        return factory.create(args);
    }
}
