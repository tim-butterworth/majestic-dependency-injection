package com.reflecty.testModels;

import com.reflecty.annotations.Random;
import com.reflecty.annotations.Singleton;

@Random
@Singleton
public interface RandomSingletonStringInterface {
    String getSomeValue();
}
