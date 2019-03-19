package kr.entree.messagereplacer.replacer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class NormalReplacer implements Replacer {
    private final List<String> from;
    private final String to;

    public NormalReplacer(List<String> from, String to) {
        this.from = from;
        this.to = to;
    }

    public static NormalReplacer of(List<String> from, String to) {
        return new NormalReplacer(from, to);
    }

    public static NormalReplacer parse(Map<?, ?> map) {
        Object from = Objects.requireNonNull(map.get("from"));
        Object to = Objects.requireNonNull(map.get("to"));
        return of(
                from instanceof Collection
                        ? ((Collection<?>) from).stream().map(Object::toString).collect(Collectors.toList())
                        : Collections.singletonList(from.toString()),
                to.toString()
        );
    }

    @Override
    public String replace(String text) {
        for (String match : from) {
            text = text.replace(match, to);
        }
        return text;
    }
}
