package com.reflecty.cachingStrategies;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

public interface CachingStrategy {
    <T> T getInstance(Instantiator instantiator, DecoratedClass<T> containedClass);
}
