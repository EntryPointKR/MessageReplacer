package com.github.entrypointkr.messagereplacer.utils;

import java.util.Optional;

/**
 * Created by JunHyeong on 2018-08-23
 */
public class Booleans {
    public static Optional<Boolean> parseBoolean(String contents) {
        try {
            return Optional.of(Boolean.parseBoolean(contents));
        } catch (Exception ex) {
            // Ignore
        }
        return Optional.empty();
    }
}
