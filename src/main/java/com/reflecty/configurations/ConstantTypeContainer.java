package com.reflecty.configurations;

public interface ConstantTypeContainer<T> {
    boolean matches(DecoratedClass<?> aClass);
    T getConstant();
    Class<T> getConstantClass();
}
