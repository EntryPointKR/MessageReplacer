package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.handler.PlayerChatCacher;

import java.util.function.Predicate;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class PredicateReplacer implements Replacer {
    private final Replacer replacer;
    private final Predicate<String> validator;

    public static PredicateReplacer ofNonPlayerChat(Replacer replacer) {
        return new PredicateReplacer(replacer, contents -> {
            for (String chat : PlayerChatCacher.MESSAGES) {
                if (contents.endsWith(chat)) {
                    return false;
                }
            }
            return true;
        });
    }

    public PredicateReplacer(Replacer replacer, Predicate<String> validator) {
        this.replacer = replacer;
        this.validator = validator;
    }

    @Override
    public String replace(String content) {
        if (validator.test(content)) {
            return replacer.replace(content);
        }
        return content;
    }
}
