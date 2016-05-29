package com.reflecty.instantiators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.helperObjects.ObjectContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreateObjectFromConstructor {
    private ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer;

    public CreateObjectFromConstructor(ObjectContainer<ObjectBuilderMachine> objectBuilderMachineObjectContainer) {
        this.objectBuilderMachineObjectContainer = objectBuilderMachineObjectContainer;
    }

    public <T> Object[] buildConstructorArguments(Constructor<?> constructor, Set<Class<?>> classSet) {
        Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        return IntStream.range(0, parameterTypes.length)
                .mapToObj(i -> objectBuilderMachineObjectContainer.getContents()
                        .getInstance(
                                (Class<T>) parameterTypes[i],
                                classSet,
                                parameterAnnotations[i]
                        )).collect(Collectors.toList()).toArray();
    }
}