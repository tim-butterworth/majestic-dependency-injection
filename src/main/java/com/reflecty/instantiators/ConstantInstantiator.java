package com.reflecty.instantiators;

import com.reflecty.configurations.ConstantModule;
import com.reflecty.configurations.DecoratedClass;

public class ConstantInstantiator implements Instantiator {
    private ConstantModule constantModule;

    public ConstantInstantiator(ConstantModule constantModule) {
        this.constantModule = constantModule;
    }

    @Override
    public <T> T instantiate(DecoratedClass<T> decoratedClass) {
        return constantModule.findMatch(decoratedClass);
    }
}
