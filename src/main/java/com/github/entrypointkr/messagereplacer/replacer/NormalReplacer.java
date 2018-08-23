package com.github.entrypointkr.messagereplacer.replacer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

import static org.apache.commons.lang.ArrayUtils.INDEX_NOT_FOUND;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class NormalReplacer implements Replacer {
    private final String from;
    private final String to;
    private final Boolean ignoreCase;

    public static String replaceIgnoreCase(String text, String target, String replacement, int max) {
        if (isEmpty(text) || isEmpty(target) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = indexOfIgnoreCase(text, target, start);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        int replLength = target.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StrBuilder buf = new StrBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = indexOfIgnoreCase(text, target, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static int indexOfIgnoreCase(String text, String target, int startPos) {
        if (text == null || target == null) {
            return INDEX_NOT_FOUND;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (text.length() - target.length()) + 1;
        if (startPos > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (target.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (text.regionMatches(true, i, target, 0, target.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public NormalReplacer(String from, String to, Boolean ignoreCase) {
        this.from = from;
        this.to = to;
        this.ignoreCase = ignoreCase;
    }

    @Override
    public String replace(String content) {
        return ignoreCase
                ? replaceIgnoreCase(content, from, to, -1)
                : StringUtils.replace(content, from, to);
    }
}
