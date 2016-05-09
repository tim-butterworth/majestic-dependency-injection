package com.reflecty.configurations;

public class TypeMatchingContainer<T> implements MatchingContainer<T> {

    @Override
    public boolean matches(DecoratedClass<?> aClass) {
        return false;
    }

    @Override
    public T getContent() {
        return null;
    }

    @Override
    public Class<T> getConstantClass() {
        return null;
    }
}
