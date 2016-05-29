package com.reflecty.creators;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

import java.util.Set;

public class InstanceCreatorMachine {
    public <T> T getInstance(DecoratedClass<T> decoratedClass, CachingStrategy cachingStrategy, Instantiator instantiator, Set<Class<?>> classSet) {
        return cachingStrategy.getInstance(instantiator, decoratedClass, classSet);
    }
}
