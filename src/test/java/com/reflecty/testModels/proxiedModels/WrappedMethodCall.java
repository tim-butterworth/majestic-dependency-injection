package com.reflecty.testModels.proxiedModels;

import com.reflecty.testModels.ConfigureableObject;
import com.reflecty.testModels.TestClass1;

public interface WrappedMethodCall {
    ConfigureableObject doTheThing(ConfigureableObject configureableObject);
    TestClass1 getTestClass();
}
