package com.reflecty.testModels;

import com.reflecty.annotations.Namespace;

public class ConstructorWithAnnotatedParams {

    private InterfaceForAnObject firstObj;
    private InterfaceForAnObject secondObj;
    private TestClass1 testClass1;

    public ConstructorWithAnnotatedParams(
            @Namespace("One") InterfaceForAnObject firstObj,
            @Namespace("Two") InterfaceForAnObject secondObj,
            TestClass1 testClass1
    ) {
        this.firstObj = firstObj;
        this.secondObj = secondObj;
        this.testClass1 = testClass1;
    }

    public InterfaceForAnObject getSecondObj() {
        return secondObj;
    }

    public InterfaceForAnObject getFirstObj() {
        return firstObj;
    }

    public TestClass1 getTestClass1() {
        return testClass1;
    }
}
