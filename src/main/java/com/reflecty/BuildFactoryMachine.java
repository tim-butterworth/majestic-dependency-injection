package com.reflecty;

public class BuildFactoryMachine {
    private InstanceCreatorFactory instanceCreatorFactory;

    public BuildFactoryMachine(InstanceCreatorFactory instanceCreatorFactory) {
        this.instanceCreatorFactory = instanceCreatorFactory;
    }

    public <T> T buildItRealWell(Class<T> clazz) {
        return instanceCreatorFactory.getInstanceCreator(clazz).getInstance(clazz);
    }

}
