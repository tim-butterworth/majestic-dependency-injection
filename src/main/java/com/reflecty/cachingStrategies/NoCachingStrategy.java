package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

public class NoCachingStrategy implements CachingStrategy {
    @Override
    public <T> T getInstance(Instantiator instantiator, DecoratedClass<T> decoratedClass) {
        return instantiator.instantiate(decoratedClass);
    }
}
