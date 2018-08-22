package com.github.entrypointkr.messagereplacer.replacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class CombinedReplacer implements ICombinedReplacer {
    private final List<Replacer> replacers = new ArrayList<>();

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
