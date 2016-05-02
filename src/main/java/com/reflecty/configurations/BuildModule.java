package com.reflecty.configurations;

import com.reflecty.annotations.Namespace;

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

    public Object getMatch(DecoratedClass<?> decoratedClass) {
        Optional<? extends Class<?>> matchOptional = registryCache.entrySet().stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .filter(value -> value.equals(getTypeMatcher(decoratedClass)))
                        .map(value -> entry.getKey()))
                .findFirst();

        return matchOptional.orElse(null);
    }

    private TypeMatcher getTypeMatcher(DecoratedClass<?> decoratedClass) {
        Class<?> containedClass = decoratedClass.getContainedClass();
        decoratedClass.getExtraAnnotations();

        return Arrays.asList(decoratedClass.getExtraAnnotations()).stream()
                .filter(annotation -> annotation instanceof Namespace)
                .map(annotation -> (Namespace) annotation)
                .findFirst()
                .map(namespaceAnnotation -> (TypeMatcher) new NamespaceTypeMatcherImpl<>(namespaceAnnotation.value(), containedClass))
                .orElse(new TypeMatcherImpl<>(containedClass));
    }
}
