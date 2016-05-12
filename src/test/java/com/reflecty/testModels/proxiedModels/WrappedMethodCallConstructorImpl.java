package com.reflecty.testModels.proxiedModels;

import com.reflecty.annotations.DoAfter;
import com.reflecty.annotations.DoBefore;
import com.reflecty.testModels.AfterConfigureAThingFunction;
import com.reflecty.testModels.BeforeConfigureAThingFunction;
import com.reflecty.testModels.ConfigureableObject;
import com.reflecty.testModels.TestClass1;

public class WrappedMethodCallConstructorImpl implements WrappedMethodCall {

    private TestClass1 testClass;

    public WrappedMethodCallConstructorImpl(TestClass1 testClass) {
        this.testClass = testClass;
    }

    @Override
    @DoBefore(BeforeConfigureAThingFunction.class)
    @DoAfter(AfterConfigureAThingFunction.class)
    public ConfigureableObject doTheThing(ConfigureableObject configureableObject) {
        configureableObject.setStringFromMethod("This string comes from the method call");
        return configureableObject;
    }


    public TestClass1 getTestClass() {
        return testClass;
    }

}
