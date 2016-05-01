package com.reflecty.cachingStrategies;

import com.reflecty.instantiators.Instantiator;

public interface CachingStrategy {
    <T> T getInstance(Class<T> tClass, Instantiator instantiator);
}
