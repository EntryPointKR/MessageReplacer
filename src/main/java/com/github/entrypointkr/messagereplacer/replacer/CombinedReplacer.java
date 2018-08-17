package com.github.entrypointkr.messagereplacer.replacer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class CombinedReplacer implements ICombinedReplacer {
    private final Set<Replacer> replacers = new HashSet<>();

    @Override
    public String replace(String content) {
        for (Replacer replacer : replacers) {
            content = replacer.replace(content);
        }
        return content;
    }

    public CombinedReplacer addReplacer(Replacer... replacers) {
        this.replacers.addAll(Arrays.asList(replacers));
        return this;
    }

    @Override
    public CombinedReplacer removeReplacer(Replacer... replacers) {
        this.replacers.removeAll(Arrays.asList(replacers));
        return this;
    }

    @Override
    public CombinedReplacer clear() {
        replacers.clear();
        return this;
    }
}
