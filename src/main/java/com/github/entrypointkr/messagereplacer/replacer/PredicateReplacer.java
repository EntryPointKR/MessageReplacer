package com.github.entrypointkr.messagereplacer.replacer;

import java.util.function.Predicate;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class PredicateReplacer implements Replacer {
    private final Predicate<String> validator;
    private final Replacer replacer;
    private final Replacer elseRep;

    public PredicateReplacer(Predicate<String> validator, Replacer replacer, Replacer elseRep) {
        this.validator = validator;
        this.replacer = replacer;
        this.elseRep = elseRep;
    }

    public PredicateReplacer(Predicate<String> validator, Replacer replacer) {
        this(validator, replacer, IdentityReplacer.INSTANCE);
    }

    @Override
    public String replace(String content) {
        return validator.test(content)
                ? replacer.replace(content)
                : elseRep.replace(content);
    }
}
