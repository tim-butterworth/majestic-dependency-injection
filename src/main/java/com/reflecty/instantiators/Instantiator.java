package com.reflecty.instantiators;

public interface Instantiator {
    <T> T instantiate(Class<T> clazz);
}
