package com.reflecty.creators;

public class DefaultInstanceCreator implements InstanceCreator {

    private final ReflectiveInstantiator reflectiveInstantiator = new ReflectiveInstantiator();

    @Override
    public <T> T getInstance(Class<T> tClass) {
        return reflectiveInstantiator.instantiate(tClass);
    }
}
