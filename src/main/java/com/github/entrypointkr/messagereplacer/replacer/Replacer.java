package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.Reflections;

/**
 * Created by JunHyeong on 2018-08-17
 */
public interface Replacer {
    String replace(String content);

    @SuppressWarnings("unchecked")
    default Object replaceChatComponent(Object chatComponent) {
        String flatText = Reflections.chatComponentToFlatString(chatComponent);
        String replaced = replace(flatText);
        if (!flatText.equals(replaced)) {
            return Reflections.createChatComponent(replaced)[0];
        } else {
            return chatComponent;
        }
    }
}
