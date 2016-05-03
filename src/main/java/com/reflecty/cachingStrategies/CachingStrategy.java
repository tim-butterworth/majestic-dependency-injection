package com.reflecty.cachingStrategies;

import com.reflecty.instantiators.Instantiator;

public interface CachingStrategy {
    <T> T getInstance(Instantiator instantiator, Class<T> containedClass);
}
