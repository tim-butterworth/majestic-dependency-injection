package com.reflecty.creators;

public class DefaultInstanceCreator implements InstanceCreator {

    private final ReflectiveInstantiator reflectiveInstantiator;

    public DefaultInstanceCreator(ReflectiveInstantiator reflectiveInstantiator) {
        this.reflectiveInstantiator = reflectiveInstantiator;
    }

    @Override
    public <T> T getInstance(Class<T> tClass) {
        return reflectiveInstantiator.instantiate(tClass);
    }
}
