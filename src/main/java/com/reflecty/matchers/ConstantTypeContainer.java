package com.reflecty.matchers;

public class ConstantTypeContainer<T> {
    private final T one;
    private final Class<T> constantClass;

    public ConstantTypeContainer(T one, Class<T> tClass) {
        this.one = one;
        this.constantClass = tClass;
    }

    public boolean matches(Class<?> aClass) {
        return aClass.equals(constantClass);
    }

    public T getConstant() {
        return one;
    }
}
