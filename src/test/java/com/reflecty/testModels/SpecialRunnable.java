package com.reflecty.testModels;

import java.util.function.Function;

public class SpecialRunnable<T> implements Runnable {
    private T instance;
    private Class<T> clazz;
    private Function<Class<T>, T> function;

    public SpecialRunnable(Function<Class<T>, T> function, Class<T> clazz) {
        this.function = function;
        this.clazz = clazz;
    }

    @Override
    public void run() {
        instance = function.apply(clazz);
    }

    public T getInstance() {
        return instance;
    }
}
