package com.reflecty.testModels;

public class ConstructorTonClass {

    private SingleTonClass singleTonClass;

    public ConstructorTonClass(SingleTonClass singleTonClass) {
        this.singleTonClass = singleTonClass;
    }

    public SingleTonClass getSingleTonClass() {
        return singleTonClass;
    }
}
