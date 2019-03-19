package kr.entree.messagereplacer.replacer;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class IdentityReplacer implements Replacer {
    public static final IdentityReplacer INSTANCE = new IdentityReplacer();

    private IdentityReplacer() {
    }

    @Override
    public String replace(String text) {
        return text;
    }
}
