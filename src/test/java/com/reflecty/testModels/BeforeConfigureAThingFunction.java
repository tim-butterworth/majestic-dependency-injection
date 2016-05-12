package com.reflecty.testModels;

import java.util.function.Function;

public class BeforeConfigureAThingFunction implements Function<ConfigureableObject, ConfigureableObject> {

    @Override
    public ConfigureableObject apply(ConfigureableObject configureableObject) {
        configureableObject.setStringFromBefore("Setting the string to be before the method invocation");
        return configureableObject;
    }
}
