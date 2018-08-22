package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.utils.Booleans;
import com.github.entrypointkr.messagereplacer.utils.OptionalMap;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by JunHyeong on 2018-08-17
 */
public enum ReplacerType {
    DEFAULT("normal", map -> {
        String from = getOrThrow(map, "from").toString();
        String to = getOrThrow(map, "to").toString();
        Boolean ignoreCase = map.getOptional("ignore_case")
                .map(Object::toString)
                .flatMap(Booleans::parseBoolean)
                .orElse(true);
        return new NormalReplacer(from, ColorizeReplacer.colorize(to), ignoreCase);
    }),
    REGEX("regex", map -> {
        String from = getOrThrow(map, "from").toString();
        String to = getOrThrow(map, "to").toString();
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

    private static <T> T getOrThrow(Map<?, ?> map, Object key, Function<Optional<Object>, Optional<T>> modifier) {
        return modifier.apply(Optional.ofNullable(map.get(key)))
                .orElseThrow(() -> new IllegalArgumentException("The value of a given \"" + key + "\" is null. Please check the config file."));
    }

    private static Object getOrThrow(Map<?, ?> map, Object key) {
        return getOrThrow(map, key, Function.identity());
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
