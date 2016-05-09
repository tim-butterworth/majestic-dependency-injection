package com.reflecty.testModels;

import com.reflecty.annotations.Namespace;
import com.reflecty.annotations.Singleton;
import com.reflecty.providers.ObjectProvider;

@Singleton
public class CustomInstanceProvider implements ObjectProvider<CustomConfiguredObject> {

    private FancyFactory fancyFactory;

    public CustomInstanceProvider(
            @Namespace("OracleFactory") FancyFactory fancyFactory
    ) {
        this.fancyFactory = fancyFactory;
    }

    @Override
    public CustomConfiguredObject getInstance() {
        CustomConfiguredObject customConfiguredObject = new CustomConfiguredObject();
        customConfiguredObject.setFancyFactory(fancyFactory);
        return customConfiguredObject;
    }
}
