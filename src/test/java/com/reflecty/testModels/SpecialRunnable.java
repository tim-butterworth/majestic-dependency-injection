package com.reflecty.testModels;

import com.reflecty.ObjectBuilderMachine;

public class SpecialRunnable<T> implements Runnable {
    private T instance;
    private ObjectBuilderMachine buildFactoryMachine;
    private Class<T> clazz;


    public SpecialRunnable(ObjectBuilderMachine buildFactoryMachine, Class<T> clazz) {
        this.buildFactoryMachine = buildFactoryMachine;
        this.clazz = clazz;
    }

    @Override
    public void run() {
        instance = buildFactoryMachine.getInstance(clazz);
    }

    public T getInstance() {
        return instance;
    }
}
