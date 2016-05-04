package com.reflecty.instantiators;

import com.reflecty.configurations.DecoratedClass;

public interface Instantiator {
    <T> T instantiate(DecoratedClass<T> decoratedClass);
}
