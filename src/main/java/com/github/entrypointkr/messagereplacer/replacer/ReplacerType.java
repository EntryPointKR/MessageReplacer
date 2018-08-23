package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.utils.Booleans;
import com.github.entrypointkr.messagereplacer.utils.OptionalMap;

import java.util.function.Function;

import static com.github.entrypointkr.messagereplacer.replacer.Replacers.NULL_MESSAGE;

/**
 * Created by JunHyeong on 2018-08-17
 */
public enum ReplacerType {
    DEFAULT("normal", map -> {
        String from = map.getOrThrow("from", NULL_MESSAGE).toString();
        String to = map.getOrThrow("to", NULL_MESSAGE).toString();
        Boolean ignoreCase = map.getOptional("ignore_case")
                .map(Object::toString)
                .flatMap(Booleans::parseBoolean)
                .orElse(true);
        return new NormalReplacer(from, ColorizeReplacer.colorize(to), ignoreCase);
    }),
    REGEX("regex", map -> {
        String from = map.getOrThrow("from", NULL_MESSAGE).toString();
        String to = map.getOrThrow("to", NULL_MESSAGE).toString();
        return new RegexReplacer(from, ColorizeReplacer.colorize(to));
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
    private final Function<OptionalMap<?, ?>, Replacer> factory;

    ReplacerType(String name, Function<OptionalMap<?, ?>, Replacer> factory) {
        this.name = name;
        this.factory = factory;
    }

    public Replacer createReplacer(OptionalMap<?, ?> paramMap) {
        return factory.apply(paramMap);
    }
}
