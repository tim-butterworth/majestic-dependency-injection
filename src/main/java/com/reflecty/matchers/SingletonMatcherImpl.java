package com.reflecty.matchers;

import com.reflecty.annotations.Singleton;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SingletonMatcherImpl implements Matcher {
    @Override
    public <T> boolean canHandle(Class<T> clazz) {
        return !Arrays.asList(clazz.getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Singleton)
                .collect(Collectors.toList()).isEmpty();
    }
}
