package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.handler.PlayerChatCacher;

/**
 * Created by JunHyeong on 2018-08-23
 */
public class ApplicationReplacer implements Replacer {
    private final Replacer replacer;
    private final Replacer decolorizer;

    public ApplicationReplacer(Replacer replacer, Replacer decolorizer) {
        this.replacer = replacer;
        this.decolorizer = decolorizer;
    }

    public ApplicationReplacer(Replacer replacer) {
        this(replacer, DecolorizeReplacer.INSTANCE);
    }

    @Override
    public String replace(String content) {
        String decolorized = decolorizer.replace(content);
        String replaced = replacer.replace(decolorized);
        PlayerChatCacher.MESSAGES.remove(decolorized);
        return decolorized.equals(replaced)
                ? content
                : replaced;
    }
}
