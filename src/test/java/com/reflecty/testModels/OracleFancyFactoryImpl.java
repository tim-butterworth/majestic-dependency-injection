package com.reflecty.testModels;

import com.reflecty.annotations.Singleton;

@Singleton
public class OracleFancyFactoryImpl implements FancyFactory {

    private synchronized SomeSortOfConnection getInstance() {
        return new OracleSortOfConnection();
    }
}
