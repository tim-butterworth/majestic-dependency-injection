package com.reflecty.configurations;

import java.lang.annotation.Annotation;

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
}
