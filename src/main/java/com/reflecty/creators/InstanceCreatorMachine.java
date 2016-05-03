package com.reflecty.creators;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.instantiators.Instantiator;

public class InstanceCreatorMachine {

    public <T> T getInstance(Class<T> tClass, CachingStrategy cachingStrategy, Instantiator instantiator) {
        return cachingStrategy.getInstance(instantiator, tClass);
    }

}
