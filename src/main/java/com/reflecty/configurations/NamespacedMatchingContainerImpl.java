package com.reflecty.configurations;

import com.reflecty.annotations.Constant;

import java.util.Arrays;

public class NamespacedMatchingContainerImpl<T> implements MatchingContainer<T> {

    private final String namespace;
    private final T value;
    private final Class<T> tClass;

    public NamespacedMatchingContainerImpl(String namespace, T constant, Class<T> tClass) {
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
    public T getContent() {
        return value;
    }

    @Override
    public Class<T> getConstantClass() {
        return tClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamespacedMatchingContainerImpl<?> that = (NamespacedMatchingContainerImpl<?>) o;

        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) return false;
        return tClass != null ? tClass.equals(that.tClass) : that.tClass == null;

    }

    @Override
    public int hashCode() {
        int result = namespace != null ? namespace.hashCode() : 0;
        result = 31 * result + (tClass != null ? tClass.hashCode() : 0);
        return result;
    }
}
