package com.reflecty.testModels;

import java.util.Random;

public class NonSingleTonClass {

    private String name;

    public NonSingleTonClass() {
        this.name = String.valueOf(new Random().nextLong());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
