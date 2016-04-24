package com.reflecty.creators;

public interface InstanceCreator {
    <T> T getInstance(Class<T> tClass);
}
