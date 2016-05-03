package com.reflecty.cachingStrategies;

import com.reflecty.instantiators.Instantiator;

public class NoCachingStrategy implements CachingStrategy {
    @Override
    public <T> T getInstance(Instantiator instantiator, Class<T> containedClass) {
        return instantiator.instantiate(containedClass);
    }
}
