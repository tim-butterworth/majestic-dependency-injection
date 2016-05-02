package com.reflecty.instantiators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.annotations.Namespace;
import com.reflecty.configurations.*;
import com.reflecty.helperObjects.ObjectContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReflectiveInstantiator implements Instantiator {

    private ObjectContainer<ObjectBuilderMachine> objectBuilderMachineContainer;
    private BuildModule module;

    public ReflectiveInstantiator(ObjectContainer<ObjectBuilderMachine> objectBuilderMachineContainer, BuildModule module) {
        this.objectBuilderMachineContainer = objectBuilderMachineContainer;
        this.module = module;
    }

    @Override
    public <T> T instantiate(DecoratedClass<T> classContainer) {
        Class<T> containedClass = classContainer.getContainedClass();
        Class<?> registeredClass = null;
        try {
            registeredClass = getRegisteredClass(
                    containedClass,
                    new DecoratedClass<>(containedClass)
            );
            return newInstanceFromConstructor(
                    registeredClass.getDeclaredConstructors()[0]
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + registeredClass + " does not have an appropriate constructor", e);
        }
    }

    private <T> T newInstanceFromConstructor(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return (T) constructor.newInstance(buildConstructorArguments(constructor));
    }

    private <T> Object[] buildConstructorArguments(Constructor<?> constructor) {
        Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        return IntStream.range(0, parameterTypes.length)
                .mapToObj(i -> {
                    Class<T> aClass = (Class<T>)parameterTypes[i];
                    DecoratedClass<T> decoratedClass = new DecoratedClass<>(aClass, parameterAnnotations[i]);
                    return objectBuilderMachineContainer.getContents().getInstance(getRegisteredClass(aClass, decoratedClass));
                }).collect(Collectors.toList()).toArray();
    }

    private String getNamespace(Annotation[] parameterAnnotation) {
        return Arrays.asList(parameterAnnotation).stream()
                .filter(annotation -> annotation instanceof Namespace)
                .map(annotation -> {
                    Namespace namespace = (Namespace) annotation;
                    return namespace.value();
                }).findFirst().orElse("default");
    }

    private <T> Class<? extends T> getRegisteredClass(Class<T> clazz, DecoratedClass<T> decoratedClass) {
        Class<? extends T> match = (Class<? extends T>) module.getMatch(decoratedClass);
        if (match == null) match = clazz;
        return match;
    }
}