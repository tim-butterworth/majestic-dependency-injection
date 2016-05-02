package com.reflecty.configurations;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class DecoratedClass<T> {
    private final Class<T> clazz;
    private final Annotation[] extraAnnotations;

    public DecoratedClass(Class<T> clazz, Annotation... extraAnnotations) {
        this.clazz = clazz;
        this.extraAnnotations = extraAnnotations;
    }

    public Class<T> getContainedClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecoratedClass<?> that = (DecoratedClass<?>) o;

        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(extraAnnotations, that.extraAnnotations);

    }

    @Override
    public int hashCode() {
        int result = clazz != null ? clazz.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(extraAnnotations);
        return result;
    }

    public Annotation[] getExtraAnnotations() {
        return extraAnnotations;
    }
}
