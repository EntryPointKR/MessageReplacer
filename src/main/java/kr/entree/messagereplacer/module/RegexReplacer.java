package kr.entree.messagereplacer.module;

import org.apache.commons.lang.Validate;

import java.util.Map;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class RegexReplacer implements Replacer {
    private final String regex;
    private final String to;

    private RegexReplacer(String regex, String to) {
        this.regex = regex;
        this.to = to;
    }

    public static RegexReplacer of(String regex, String to) {
        return new RegexReplacer(regex, to);
    }

    public static RegexReplacer parse(Map<?, ?> map) {
        Object from = map.get("from");
        Object to = map.get("to");
        Validate.isTrue(from != null && to != null);
        return of(from.toString(), to.toString());
    }

    @Override
    public String replace(String content) {
        return content.replaceAll(regex, to);
    }
}
