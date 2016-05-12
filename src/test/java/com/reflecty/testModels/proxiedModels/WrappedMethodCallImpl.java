package com.reflecty.testModels.proxiedModels;

import com.reflecty.annotations.DoAfter;
import com.reflecty.annotations.DoBefore;
import com.reflecty.annotations.Proxied;
import com.reflecty.testModels.AfterConfigureAThingFunction;
import com.reflecty.testModels.BeforeConfigureAThingFunction;
import com.reflecty.testModels.ConfigureableObject;
import com.reflecty.testModels.TestClass1;
import com.reflecty.testModels.proxiedModels.WrappedMethodCall;

@Proxied
public class WrappedMethodCallImpl implements WrappedMethodCall {

    @DoBefore(BeforeConfigureAThingFunction.class)
    @DoAfter(AfterConfigureAThingFunction.class)
    public ConfigureableObject doTheThing(ConfigureableObject configureableObject) {
        configureableObject.setStringFromMethod("This string comes from the method call");
        return configureableObject;
    }

    @Override
    public TestClass1 getTestClass() {
        return null;
    }

}
