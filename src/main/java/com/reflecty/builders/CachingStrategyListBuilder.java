package com.reflecty.builders;

import com.reflecty.annotations.Singleton;
import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.cachingStrategies.NoCachingStrategy;
import com.reflecty.cachingStrategies.SingletonCacheStrategy;
import com.reflecty.matchers.ObjectMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CachingStrategyListBuilder {
    public List<ObjectMatcher<Class<?>, CachingStrategy>> getCachingStrategiesList() {
        Function<Class<?>, Boolean> matchAllForCaching = o -> true;
        Function<Class<?>, Boolean> matchSingletonCaching = aClass -> !Arrays.asList(aClass.getDeclaredAnnotations())
                .stream()
                .filter(annotation -> annotation instanceof Singleton)
                .collect(Collectors.toList()).isEmpty();

        return Arrays.asList(
                new ObjectMatcher<>(matchSingletonCaching, new SingletonCacheStrategy()),
                new ObjectMatcher<>(matchAllForCaching, new NoCachingStrategy())
        );
    }
}