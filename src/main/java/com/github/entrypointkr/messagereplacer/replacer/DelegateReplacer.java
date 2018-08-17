package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-17
 */
public abstract class DelegateReplacer implements Replacer {
    protected final Replacer delegate;

    public DelegateReplacer(Replacer delegate) {
        this.delegate = delegate;
    }

    @Override
    public String replace(String content) {
        return delegate.replace(content);
    }
}
