package com.reflecty.creators;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.Instantiator;

public class InstanceCreatorMachine {
    public <T> T getInstance(DecoratedClass<T> decoratedClass, CachingStrategy cachingStrategy, Instantiator instantiator) {
        return cachingStrategy.getInstance(instantiator, decoratedClass);
    }
}
