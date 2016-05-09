package com.reflecty.testModels;

public class CustomConfiguredObject {
    private FancyFactory fancyFactory;

    public void setFancyFactory(FancyFactory fancyFactory) {
        this.fancyFactory = fancyFactory;
    }

    public FancyFactory getFancyFactory() {
        return fancyFactory;
    }
}
