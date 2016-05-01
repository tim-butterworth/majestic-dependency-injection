package com.reflecty.cachingStrategies;

import com.reflecty.instantiators.Instantiator;

public class NoCachingStrategy implements CachingStrategy {
    @Override
    public <T> T getInstance(Class<T> tClass, Instantiator instantiator) {
        return instantiator.instantiate(tClass);
    }
}
