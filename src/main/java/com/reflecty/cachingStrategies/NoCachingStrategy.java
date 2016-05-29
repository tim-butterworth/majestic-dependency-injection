package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

import java.util.Set;

public class NoCachingStrategy implements CachingStrategy {
    @Override
    public <T> T getInstance(Instantiator instantiator, DecoratedClass<T> decoratedClass, Set<Class<?>> classSet) {
        return instantiator.instantiate(decoratedClass, classSet);
    }
}
