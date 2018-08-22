package com.github.entrypointkr.messagereplacer.replacer;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by JunHyeong on 2018-08-22
 */
public class ScopeCombined implements ConfigurableReplacer.Combined {
    private final EnumMap<Scope, Combined> scopeMap = new EnumMap<>(Scope.class);

    public ScopeCombined put(Scope scope, Combined combined) {
        scopeMap.put(scope, combined);
        return this;
    }

    @Override
    public void addReplacer(Map<?, ?> objectMap) {
        try {
            Scope scope = Optional.ofNullable(objectMap.get("scope"))
                    .map(String::valueOf)
                    .map(Scope::parse)
                    .orElse(Scope.PLUGIN);
            Combined combined = scopeMap.get(scope);
            Replacer replacer = Replacers.createReplacer(objectMap);
            combined.addReplacer(replacer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        scopeMap.values().forEach(Combined::clear);
    }
}
