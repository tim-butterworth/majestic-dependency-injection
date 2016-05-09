package com.reflecty.configurations;

public interface MatchingContainer<T> {
    boolean matches(DecoratedClass<?> aClass);
    T getContent();
    Class<T> getConstantClass();
}
