package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectiveInstantiator implements Instantiator {

    private final CreateObjectFromConstructor createObjectFromConstructor;

    public ReflectiveInstantiator(CreateObjectFromConstructor createObjectFromConstructor) {
        this.createObjectFromConstructor = createObjectFromConstructor;
    }

    @Override
    public <T> T instantiate(DecoratedClass<T> decoratedClass) {
        try {
            Constructor<?> constructor = decoratedClass.getContainedClass().getDeclaredConstructors()[0];
            return (T) constructor.newInstance(createObjectFromConstructor.buildConstructorArguments(constructor));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + decoratedClass + " does not have an appropriate constructor", e);
        }
    }

}