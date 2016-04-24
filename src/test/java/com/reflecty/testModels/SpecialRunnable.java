package com.reflecty.testModels;

import com.reflecty.BuildFactoryMachine;

public class SpecialRunnable<T> implements Runnable {
    private T instance;
    private BuildFactoryMachine buildFactoryMachine;
    private Class<T> clazz;


    public SpecialRunnable(BuildFactoryMachine buildFactoryMachine, Class<T> clazz) {
        this.buildFactoryMachine = buildFactoryMachine;
        this.clazz = clazz;
    }

    @Override
    public void run() {
        instance = buildFactoryMachine.buildItRealWell(clazz);
    }

    public T getInstance() {
        return instance;
    }
}
