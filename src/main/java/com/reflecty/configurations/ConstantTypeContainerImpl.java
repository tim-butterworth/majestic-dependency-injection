package com.reflecty.configurations;

public class ConstantTypeContainerImpl<T> implements ConstantTypeContainer<T> {

    private final T one;
    private final Class<T> constantClass;

    public ConstantTypeContainerImpl(T constant, Class<T> tClass) {
        this.one = constant;
        this.constantClass = tClass;
    }

    @Override
    public boolean matches(DecoratedClass<?> decoratedClass) {
        return constantClass.equals(decoratedClass.getContainedClass());
    }

    @Override
    public T getConstant() {
        return one;
    }

    @Override
    public Class<T> getConstantClass() {
        return constantClass;
    }
}
