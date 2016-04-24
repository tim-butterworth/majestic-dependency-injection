package com.reflecty.testModels;

import com.reflecty.annotations.Singleton;

import java.util.Random;

@Singleton
public class SingleTonClass {

    private String field;

    public SingleTonClass() {
        field = String.valueOf(new Random().nextLong());
    }

    @Override
    public String toString() {
        return "SingleTonClass{" +
                "field='" + field + '\'' +
                '}';
    }
}
