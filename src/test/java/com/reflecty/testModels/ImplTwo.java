package com.reflecty.testModels;

import com.reflecty.annotations.Singleton;

@Singleton
public class ImplTwo implements InterfaceForAnObject {
    @Override
    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
