package com.reflecty.configurations;

import java.util.*;

public class BuildModule {
    private Map<Class<?>, Set<TypeMatcher>> registryCache = new HashMap<>();

    public BuildModule register(Class<?> clazz, TypeMatcher matcher) {
        Set<TypeMatcher> typeMatchers = registryCache.get(clazz);
        if (typeMatchers == null) {
            typeMatchers = new HashSet<>();
            registryCache.put(clazz, typeMatchers);
        }
        typeMatchers.add(matcher);
        return this;
    }

    public Object getMatch(TypeMatcher matcher) {
        Optional<? extends Class<?>> matchOptional = registryCache.entrySet().stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .filter(value -> value.equals(matcher))
                        .map(value -> entry.getKey()))
                .findFirst();

        return matchOptional.orElse(null);
    }
}
