package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

import java.util.Set;

public interface CachingStrategy {
    <T> T getInstance(Instantiator instantiator, DecoratedClass<T> containedClass, Set<Class<?>> classSet);
}
