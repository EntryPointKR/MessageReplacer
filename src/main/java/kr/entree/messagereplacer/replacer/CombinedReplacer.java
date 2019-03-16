package kr.entree.messagereplacer.replacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class CombinedReplacer implements Replacer {
    private final List<Replacer> replacers;

    private CombinedReplacer(List<Replacer> replacers) {
        this.replacers = replacers;
    }

    public static CombinedReplacer of(List<Replacer> replacers) {
        return new CombinedReplacer(replacers);
    }

    public static CombinedReplacer of() {
        return of(new ArrayList<>());
    }

    public CombinedReplacer add(Replacer... replacers) {
        this.replacers.addAll(Arrays.asList(replacers));
        return this;
    }

    @Override
    public String replace(String text) {
        for (Replacer replacer : replacers) {
            text = replacer.replace(text);
        }
        return text;
    }
}
