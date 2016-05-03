package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;

public interface Instantiator {
    <T> T instantiate(Class<T> containedClass1);
}
