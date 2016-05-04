package com.reflecty.instantiators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.configurations.DecoratedClass;
import com.reflecty.helperObjects.ObjectContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReflectiveInstantiator implements Instantiator {

    private final ObjectContainer<ObjectBuilderMachine> objectBuilderMachineContainer;

    public ReflectiveInstantiator(ObjectContainer<ObjectBuilderMachine> objectBuilderMachineContainer) {
        this.objectBuilderMachineContainer = objectBuilderMachineContainer;
    }

    @Override
    public <T> T instantiate(DecoratedClass<T> decoratedClass) {
        try {
            return newInstanceFromConstructor(decoratedClass.getContainedClass().getDeclaredConstructors()[0]);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + decoratedClass + " does not have an appropriate constructor", e);
        }
    }

    private <T> T newInstanceFromConstructor(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return (T) constructor.newInstance(buildConstructorArguments(constructor));
    }

    private <T> Object[] buildConstructorArguments(Constructor<?> constructor) {
        Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        return IntStream.range(0, parameterTypes.length)
                .mapToObj(i -> objectBuilderMachineContainer.getContents()
                        .getInstance(
                                (Class<T>) parameterTypes[i],
                                parameterAnnotations[i]
                        )).collect(Collectors.toList()).toArray();
    }

}