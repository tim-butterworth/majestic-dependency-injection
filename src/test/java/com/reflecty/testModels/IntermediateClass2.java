package com.reflecty.testModels;

public class IntermediateClass2 {

    private ClassWithDeepCycle classWithDeepCycle;

    public IntermediateClass2(ClassWithDeepCycle classWithDeepCycle) {
        this.classWithDeepCycle = classWithDeepCycle;
    }
}
