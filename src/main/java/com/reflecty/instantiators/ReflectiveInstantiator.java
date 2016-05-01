package com.reflecty.instantiators;

import com.reflecty.ObjectBuilderMachine;
import com.reflecty.annotations.Namespace;
import com.reflecty.configurations.BuildModule;
import com.reflecty.configurations.NamespaceTypeMatcherImpl;
import com.reflecty.configurations.TypeMatcher;
import com.reflecty.configurations.TypeMatcherImpl;
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
    public <T> T instantiate(Class<T> clazz) {
        try {
            return newInstanceFromConstructor(
                    getRegisteredClass(
                            clazz,
                            new TypeMatcherImpl(clazz)
                    ).getDeclaredConstructors()[0]
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Class: " + getRegisteredClass(clazz, new TypeMatcherImpl(clazz)).getName() + " does not have an appropriate constructor", e);
        }
    }

    private <T> T newInstanceFromConstructor(Constructor<?> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return (T) constructor.newInstance(buildConstructorArguments(constructor));
    }

    private Object[] buildConstructorArguments(Constructor<?> constructor) {
        Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        return IntStream.range(0, parameterTypes.length)
                .mapToObj(i -> {
                    Class<?> aClass = parameterTypes[i];
                    NamespaceTypeMatcherImpl typeMatcher = new NamespaceTypeMatcherImpl<>(getNamespace(parameterAnnotations[i]), aClass);
                    return objectBuilderMachineContainer.getContents().getInstance(getRegisteredClass(aClass, typeMatcher));
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

    private <T> Class<? extends T> getRegisteredClass(Class<T> clazz, TypeMatcher typeMatcher) {
        Class<? extends T> match = (Class<? extends T>) module.getMatch(typeMatcher);
        if (match == null) match = clazz;
        return match;
    }
}