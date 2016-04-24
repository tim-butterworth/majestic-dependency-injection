package com.reflecty.chainclasses;

import com.reflecty.annotations.Singleton;
import com.reflecty.creators.SingletonInstanceCreator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MatcherForSingletonInstanceCreator {
    private final SingletonInstanceCreator singletonInstanceCreator;

    public MatcherForSingletonInstanceCreator() {
        singletonInstanceCreator = new SingletonInstanceCreator();
    }

    public MatcherForInstance<SingletonInstanceCreator, Class<?>> getSingletonInstanceCreatorClassMatcherForInstance() {
        return new MatcherForInstance<SingletonInstanceCreator, Class<?>>() {

            @Override
            public boolean matches(Class<?> clazz) {
                return hasSingletonAnnotation(clazz);
            }

            @Override
            public SingletonInstanceCreator getInstance() {
                return  singletonInstanceCreator;
            }
        };
    }

    public boolean hasSingletonAnnotation(Class<?> aClass) {
        return !Arrays.asList(aClass.getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Singleton)
                .collect(Collectors.toList()).isEmpty();
    }
}