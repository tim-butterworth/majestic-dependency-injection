package com.reflecty.configurations;

public class ConstantTypeContainerImpl<T> implements ConstantTypeContainer<T> {

    private final T constant;
    private final Class<T> constantClass;

    public ConstantTypeContainerImpl(T constant, Class<T> tClass) {
        this.constant = constant;
        this.constantClass = tClass;
    }

    @Override
    public boolean matches(DecoratedClass<?> decoratedClass) {
        return constantClass.equals(decoratedClass.getContainedClass());
    }

    @Override
    public T getConstant() {
        return constant;
    }

    @Override
    public Class<T> getConstantClass() {
        return constantClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstantTypeContainerImpl<?> that = (ConstantTypeContainerImpl<?>) o;

        return constantClass != null ? constantClass.equals(that.constantClass) : that.constantClass == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (constantClass != null ? constantClass.hashCode() : 0);
        return result;
    }
}
