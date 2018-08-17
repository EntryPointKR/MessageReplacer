package com.github.entrypointkr.messagereplacer.replacer;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class RegexReplacer implements Replacer {
    private final String regex;
    private final String to;

    public RegexReplacer(String regex, String to) {
        this.regex = regex;
        this.to = to;
    }

    @Override
    public String replace(String content) {
        return content.replaceAll(regex, to);
    }
}
