package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-17
 */
public interface ReplacerFactory {
    Replacer create(Object... args);
}
