package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-17
 */
public interface Combined {
    Combined addReplacer(Replacer... replacers);

    Combined removeReplacer(Replacer... replacers);

    Combined clear();
}
