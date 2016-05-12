package com.reflecty.testModels;

import java.util.function.Function;

public class AfterConfigureAThingFunction implements Function<ConfigureableObject, ConfigureableObject> {
    @Override
    public ConfigureableObject apply(ConfigureableObject configureableObject) {
        configureableObject.setStringFromAfter("This string was added after the method invocation.");
        return configureableObject;
    }
}
