package com.reflecty.creators;

import com.reflecty.configurations.DecoratedClass;
import com.reflecty.instantiators.ReflectiveInstantiator;

public class DefaultInstanceCreator implements InstanceCreator {

    private final ReflectiveInstantiator reflectiveInstantiator;

    public DefaultInstanceCreator(ReflectiveInstantiator reflectiveInstantiator) {
        this.reflectiveInstantiator = reflectiveInstantiator;
    }

    @Override
    public <T> T getInstance(DecoratedClass<T> tClass) {
        return reflectiveInstantiator.instantiate(tClass);
    }
}
