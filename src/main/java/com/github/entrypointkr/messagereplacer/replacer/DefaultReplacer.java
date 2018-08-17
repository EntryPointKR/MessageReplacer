package com.github.entrypointkr.messagereplacer.replacer;

import org.apache.commons.lang.StringUtils;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class DefaultReplacer implements Replacer {
    private final String from;
    private final String to;

    public DefaultReplacer(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String replace(String content) {
        return StringUtils.replace(content, from, to);
    }
}
