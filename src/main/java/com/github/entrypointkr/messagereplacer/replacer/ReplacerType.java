package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.utils.Booleans;
import com.github.entrypointkr.messagereplacer.utils.OptionalMap;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.entrypointkr.messagereplacer.replacer.Replacers.NULL_MESSAGE;

/**
 * Created by JunHyeong on 2018-08-17
 */
public enum ReplacerType {
    DEFAULT("normal", map -> {
        Object from = map.getOrThrow("from", NULL_MESSAGE);
        String to = map.getOrThrow("to", NULL_MESSAGE).toString();
        Boolean ignoreCase = map.getOptional("ignore_case")
                .map(Object::toString)
                .flatMap(Booleans::parseBoolean)
                .orElse(true);
        String colorizedTo = ColorizeReplacer.colorize(to);
        if (from instanceof Collection) {
            List<String> froms = ((Collection<?>) from).stream()
                    .filter(String.class::isInstance)
                    .map(Object::toString)
                    .collect(Collectors.toList());
            if (froms.isEmpty()) {
                throw new IllegalStateException("Argument \"from\" cannot be empty.");
            }
            CombinedReplacer combinedReplacer = new CombinedReplacer();
            for (String element : froms) {
                combinedReplacer.addReplacer(new NormalReplacer(element, colorizedTo, ignoreCase));
            }
            return combinedReplacer;
        } else {
            return new NormalReplacer(from.toString(), colorizedTo, ignoreCase);
        }
    }),
    REGEX("regex", map -> {
        Object from = map.getOrThrow("from", NULL_MESSAGE);
        String to = map.getOrThrow("to", NULL_MESSAGE).toString();
        String colorizedTo = ColorizeReplacer.colorize(to);
        if (from instanceof Collection) {
            List<String> froms = ((Collection<?>) from).stream()
                    .filter(String.class::isInstance)
                    .map(Object::toString)
                    .collect(Collectors.toList());
            if (froms.isEmpty()) {
                throw new IllegalStateException("Argument \"from\" cannot be empty.");
            }
            CombinedReplacer combinedReplacer = new CombinedReplacer();
            for (String element : froms) {
                combinedReplacer.addReplacer(new RegexReplacer(element, colorizedTo));
            }
            return combinedReplacer;
        } else {
            return new RegexReplacer(from.toString(), colorizedTo);
        }
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
