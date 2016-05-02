package com.reflecty.creators;

import com.reflecty.configurations.DecoratedClass;

public interface InstanceCreator {
    <T> T getInstance(DecoratedClass<T> tClass);
}
