package com.reflecty.testModels;

public class ClassWithDeepCycle {

    private IntermediateClass intermediateClass;

    public ClassWithDeepCycle(IntermediateClass intermediateClass) {
        this.intermediateClass = intermediateClass;
    }
}
