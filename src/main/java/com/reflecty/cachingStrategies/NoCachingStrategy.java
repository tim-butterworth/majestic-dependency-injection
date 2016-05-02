package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

public class NoCachingStrategy implements CachingStrategy {
    @Override
    public <T> T getInstance(DecoratedClass<T> tClass, Instantiator instantiator) {
        return instantiator.instantiate(tClass);
    }
}
