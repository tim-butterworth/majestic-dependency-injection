package com.reflecty.chainclasses;

import com.reflecty.annotations.Singleton;
import com.reflecty.creators.ReflectiveInstantiator;
import com.reflecty.creators.SingletonInstanceCreator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MatcherForSingletonInstanceCreator implements MatcherForInstance<SingletonInstanceCreator, Class<?>> {
    private final SingletonInstanceCreator singletonInstanceCreator;

    public MatcherForSingletonInstanceCreator(ReflectiveInstantiator reflectiveInstantiator) {
        singletonInstanceCreator = new SingletonInstanceCreator(reflectiveInstantiator);
    }

    @Override
    public SingletonInstanceCreator getInstance() {
        return singletonInstanceCreator;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return !Arrays.asList(clazz.getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Singleton)
                .collect(Collectors.toList()).isEmpty();
    }

}