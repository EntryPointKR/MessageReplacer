package kr.entree.messagereplacer.module;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public class ReplacerCancelException extends ReplacerException {
    public ReplacerCancelException(Replacer replacer) {
        super(replacer);
    }

    public ReplacerCancelException(String message, Replacer replacer) {
        super(message, replacer);
    }

    public ReplacerCancelException(String message, Throwable cause, Replacer replacer) {
        super(message, cause, replacer);
    }

    public ReplacerCancelException(Throwable cause, Replacer replacer) {
        super(cause, replacer);
    }

    public ReplacerCancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Replacer replacer) {
        super(message, cause, enableSuppression, writableStackTrace, replacer);
    }
}
