package com.reflecty.creators;

public class ReflectiveInstantiator {
    public <T> T instantiate(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Class: " + clazz.getName() + " does not have a default constructor");
        }
    }
}