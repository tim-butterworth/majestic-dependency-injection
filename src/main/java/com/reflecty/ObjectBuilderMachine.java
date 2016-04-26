package com.reflecty;

import com.reflecty.creators.ReflectiveInstantiator;

public class ObjectBuilderMachine {
    private InstanceCreatorFactory instanceCreatorFactory;

    public ObjectBuilderMachine() {
        ReflectiveInstantiator reflectiveInstantiator = new ReflectiveInstantiator(this);
        this.instanceCreatorFactory = new InstanceCreatorFactory(reflectiveInstantiator);
    }

    public <T> T getInstance(Class<T> clazz) {
        return instanceCreatorFactory.getInstanceCreator(clazz).getInstance(clazz);
    }

}
