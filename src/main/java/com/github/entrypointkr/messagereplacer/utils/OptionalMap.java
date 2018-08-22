package com.github.entrypointkr.messagereplacer.utils;

import java.util.Map;
import java.util.Optional;

/**
 * Created by JunHyeong on 2018-08-23
 */
public interface OptionalMap<K, V> extends Map<K, V> {
    Optional<V> getOptional(Object key);
}
