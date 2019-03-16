package kr.entree.messagereplacer.replacer;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class ReplacerException extends RuntimeException {
    private final Replacer replacer;

    public ReplacerException(Replacer replacer) {
        this.replacer = replacer;
    }

    public ReplacerException(String message, Replacer replacer) {
        super(message);
        this.replacer = replacer;
    }

    public ReplacerException(String message, Throwable cause, Replacer replacer) {
        super(message, cause);
        this.replacer = replacer;
    }

    public ReplacerException(Throwable cause, Replacer replacer) {
        super(cause);
        this.replacer = replacer;
    }

    public ReplacerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Replacer replacer) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.replacer = replacer;
    }

    public Replacer getReplacer() {
        return replacer;
    }
}
