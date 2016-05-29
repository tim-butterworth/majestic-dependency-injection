package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;

import java.util.Set;

public interface Instantiator {
    <T> T instantiate(DecoratedClass<T> decoratedClass, Set<Class<?>> classSet);
}
