package com.reflecty;

public class ObjectBuilderMachine {
    private InstanceCreatorFactory instanceCreatorFactory;

    public ObjectBuilderMachine(InstanceCreatorFactory instanceCreatorFactory) {
        this.instanceCreatorFactory = instanceCreatorFactory;
    }

    public <T> T getInstance(Class<T> clazz) {
        return instanceCreatorFactory.getInstanceCreator(clazz).getInstance(clazz);
    }

}
