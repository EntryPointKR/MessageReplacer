package com.github.entrypointkr.messagereplacer.replacer;

import com.github.entrypointkr.messagereplacer.utils.DelegateOptionalMap;
import com.github.entrypointkr.messagereplacer.utils.OptionalMap;
import com.github.entrypointkr.messagereplacer.utils.Reflections;

import java.util.Map;

/**
 * Created by JunHyeong on 2018-08-17
 */
public class Replacers {
    public static Replacer createReplacer(Map<?, ?> objectMap) throws Exception {
        OptionalMap<?, ?> map = new DelegateOptionalMap<>(objectMap);
        String typeStr = map.getOptional("type")
                .map(Object::toString)
                .orElse("normal");
        return createReplacer(typeStr, map);
    }

    public static Replacer createReplacer(String typeStr, OptionalMap<?, ?> paramMap) throws Exception {
        return ReplacerType.getType(typeStr)
                .createReplacer(paramMap);
    }

    public static Object replaceChatComponent(Replacer replacer, Object chatComponent) {
        String flatText = Reflections.chatComponentToFlatString(chatComponent);
        String replaced = replacer.replace(flatText);
        if (!flatText.equals(replaced)) {
            return Reflections.createChatComponent(replaced)[0];
        } else {
            return chatComponent;
        }
    }

    private Replacers() {
    }
}
