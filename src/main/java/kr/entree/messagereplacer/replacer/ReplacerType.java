package kr.entree.messagereplacer.replacer;

import java.util.Map;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public enum ReplacerType implements Named, ReplacerParser {
    NORMAL("normal", NormalReplacer::parse),
    REGEX("regex", RegexReplacer::parse),
    IGNORE("ignore", IgnoreReplacer::parse),
    ;

    private final String name;
    private final ReplacerParser parser;

    ReplacerType(String name, ReplacerParser parser) {
        this.name = name;
        this.parser = parser;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Replacer parse(Map<?, ?> data) {
        return parser.parse(data);
    }
}
