package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class ChainReplacer implements Replacer {
    private final Replacer first;
    private final Replacer last;

    public ChainReplacer(Replacer first, Replacer last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public String replace(String content) {
        String modified = first.replace(content);
        return modified != null
                ? last.replace(modified)
                : content;
    }
}
