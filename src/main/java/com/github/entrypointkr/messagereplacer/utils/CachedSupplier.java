package com.github.entrypointkr.messagereplacer.utils;

import java.util.function.Supplier;

/**
 * Created by JunHyeong on 2018-08-22
 */
public class CachedSupplier<T> implements Supplier<T> {
    private final Supplier<T> supplier;
    private T cached = null;

    public CachedSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (cached == null) {
            cached = supplier.get();
        }
        return cached;
    }
}
