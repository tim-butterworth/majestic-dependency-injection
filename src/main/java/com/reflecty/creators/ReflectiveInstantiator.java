package com.reflecty.creators;

import com.reflecty.BuildFactoryMachine;
import com.reflecty.InstanceCreatorFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectiveInstantiator {
    public <T> T instantiate(Class<T> clazz) {
        try {
            return newInstanceFromConstructor(clazz.getDeclaredConstructors()[0], new BuildFactoryMachine(new InstanceCreatorFactory()));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + clazz.getName() + " does not have an appropriate constructor", e);
        }
    }

    private <T> T newInstanceFromConstructor(Constructor<?> constructor, BuildFactoryMachine buildFactoryMachine) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return (T) constructor.newInstance(
                Arrays.<Class<?>>asList(
                        constructor.getParameterTypes()
                ).stream().map(buildFactoryMachine::buildItRealWell).collect(Collectors.toList()).toArray()
        );
    }
}