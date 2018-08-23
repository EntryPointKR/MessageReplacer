package com.github.entrypointkr.messagereplacer.utils;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by JunHyeong on 2018-08-23
 */
public interface OptionalMap<K, V> extends Map<K, V> {
    default Optional<V> getOptional(Object key) {
        return Optional.ofNullable(get(key));
    }

    default <T> T getOrThrow(Object key, String mesage, Function<Optional<V>, Optional<T>> modifier) {
        return modifier.apply(getOptional(key))
                .orElseThrow(() -> new IllegalArgumentException(String.format(mesage, key)));
    }

    default V getOrThrow(Object key, String message) {
        return getOrThrow(key, message, Function.identity());
    }
}
