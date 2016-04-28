package com.reflecty.testModels;

public class ImplOne implements InterfaceForAnObject {
    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
