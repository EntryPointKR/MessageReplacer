package kr.entree.messagereplacer.replacer;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class IgnoreReplacer implements Replacer {
    private final Pattern pattern;

    public IgnoreReplacer(Pattern pattern) {
        this.pattern = pattern;
    }

    public static IgnoreReplacer parse(Map<?, ?> map) {
        String patternStr = Objects.requireNonNull(map.get("from")).toString();
        return new IgnoreReplacer(Pattern.compile(patternStr));
    }

    @Override
    public String replace(String text) {
        if (pattern.matcher(text).find()) {
            throw new ReplacerCancelException(this);
        }
        return text;
    }
}
