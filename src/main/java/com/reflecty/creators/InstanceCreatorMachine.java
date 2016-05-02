package com.reflecty.creators;

import com.reflecty.cachingStrategies.CachingStrategy;
import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.matchers.Matcher;
import com.reflecty.instantiators.ReflectiveInstantiator;

public class InstanceCreatorMachine {

    private BuildModule module;
    private final CachingStrategy cachingStrategy;
    private final ReflectiveInstantiator defaultInstantiator;
    private final Matcher matcher;

    public InstanceCreatorMachine(
            ReflectiveInstantiator defaultInstantiator,
            BuildModule module,
            CachingStrategy cachingStrategy,
            Matcher matcher
    ) {
        this.module = module;
        this.cachingStrategy = cachingStrategy;
        this.defaultInstantiator = defaultInstantiator;
        this.matcher = matcher;
    }

    public <T> T getInstance(DecoratedClass<T> tClass) {
        return cachingStrategy.getInstance(tClass, defaultInstantiator);
    }

    public <T> boolean canHandle(Class<T> clazz) {
        return matcher.canHandle(clazz);
    }
}
