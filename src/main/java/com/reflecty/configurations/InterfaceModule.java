package com.reflecty.configurations;

import com.reflecty.annotations.Namespace;

import java.util.*;

public class InterfaceModule {
    private Map<Class<?>, Set<TypeMatcher>> registryCache = new HashMap<>();

    public InterfaceModule register(TypeMatcher matcher, Class<?> clazz) {
        Set<TypeMatcher> typeMatchers = registryCache.get(clazz);
        if (typeMatchers == null) {
            typeMatchers = new HashSet<>();
            registryCache.put(clazz, typeMatchers);
        }
        typeMatchers.add(matcher);
        return this;
    }

    public <T> Class<T> getMatch(DecoratedClass<T> decoratedClass) {
        return registryCache.entrySet().stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .filter(value -> value.equals(getTypeMatcher(decoratedClass)))
                        .map(value -> (Class<T>) entry.getKey()))
                .findFirst().orElse(decoratedClass.getContainedClass());
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
