package com.reflecty.configurations;

public class TypeMatcherImpl<T> implements TypeMatcher<T> {
    private Class<?> aClass;

    public TypeMatcherImpl(Class<?> aClass) {
        this.aClass = aClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeMatcherImpl that = (TypeMatcherImpl) o;

        return aClass != null ? aClass.equals(that.aClass) : that.aClass == null;
    }

    @Override
    public int hashCode() {
        return aClass != null ? aClass.hashCode() : 0;
    }
}
