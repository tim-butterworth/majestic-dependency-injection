package com.reflecty.configurations;

import com.reflecty.annotations.Constant;

import java.util.Arrays;

public class NamespaceConstantTypeContainer<T> implements ConstantTypeContainer<T> {

    private final String namespace;
    private final T value;
    private final Class<T> tClass;

    public NamespaceConstantTypeContainer(String namespace, T constant, Class<T> tClass) {
        this.namespace = namespace;
        this.value = constant;
        this.tClass = tClass;
    }

    @Override
    public boolean matches(DecoratedClass<?> decoratedClass) {
        Boolean namespaceMatches = Arrays.asList(decoratedClass.getExtraAnnotations()).stream()
                .filter(annotation -> annotation instanceof Constant)
                .findFirst()
                .map(annotation -> (Constant) annotation)
                .map(constant -> constant.value().equals(namespace))
                .orElse(false);

        return tClass.equals(decoratedClass.getContainedClass()) && namespaceMatches;
    }

    @Override
    public T getConstant() {
        return value;
    }

    @Override
    public Class<T> getConstantClass() {
        return tClass;
    }
}
