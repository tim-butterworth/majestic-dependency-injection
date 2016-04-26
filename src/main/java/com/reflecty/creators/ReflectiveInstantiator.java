package com.reflecty.creators;

import com.reflecty.ObjectBuilderMachine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectiveInstantiator {

    private ObjectBuilderMachine objectBuilderMachine;

    public ReflectiveInstantiator(ObjectBuilderMachine objectBuilderMachine) {
        this.objectBuilderMachine = objectBuilderMachine;
    }

    public <T> T instantiate(Class<T> clazz) {
        try {
            return newInstanceFromConstructor(clazz.getDeclaredConstructors()[0]);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + clazz.getName() + " does not have an appropriate constructor", e);
        }
    }

    private <T> T newInstanceFromConstructor(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return (T) constructor.newInstance(
                Arrays.asList(
                        constructor.getParameterTypes()
                ).stream().map(objectBuilderMachine::getInstance).collect(Collectors.toList()).toArray()
        );
    }
}