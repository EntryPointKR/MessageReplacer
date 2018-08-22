package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-18
 */
public class IdentityReplacer implements Replacer {
    public static final IdentityReplacer INSTANCE = new IdentityReplacer();

    private IdentityReplacer() {
    }

    @Override
    public String replace(String content) {
        return content;
    }
}
