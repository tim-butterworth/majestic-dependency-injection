package com.reflecty.testModels;

import com.reflecty.annotations.Constant;

public class ConstantTonClass {
    private String string;

    public ConstantTonClass(@Constant String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
