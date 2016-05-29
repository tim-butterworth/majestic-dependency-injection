package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ReflectiveInstantiator implements Instantiator {

    private final CreateObjectFromConstructor createObjectFromConstructor;

    public ReflectiveInstantiator(CreateObjectFromConstructor createObjectFromConstructor) {
        this.createObjectFromConstructor = createObjectFromConstructor;
    }

    @Override
    public <T> T instantiate(DecoratedClass<T> decoratedClass, Set<Class<?>> classSet) {
        try {
            Constructor<?> constructor = decoratedClass.getContainedClass().getDeclaredConstructors()[0];
            return (T) constructor.newInstance(createObjectFromConstructor.buildConstructorArguments(constructor, classSet));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + decoratedClass + " does not have an appropriate constructor", e);
        }
    }

}